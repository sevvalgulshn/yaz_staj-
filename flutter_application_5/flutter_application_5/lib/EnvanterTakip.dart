import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'SayimEkrani.dart';
import 'MalzemeBul.dart';
import 'main.dart';

class EnvanterTakip extends StatefulWidget {
  final String username;
  final bool isPersonelBazli;

  EnvanterTakip({required this.username, this.isPersonelBazli = false});

  @override
  _EnvanterTakipState createState() => _EnvanterTakipState();
}

class _EnvanterTakipState extends State<EnvanterTakip> {
  int totalMaterials = 0;
  bool isLoading = false;
  int? selectedYear;

  @override
  void initState() {
    super.initState();
    _fetchTotalMaterialCount();
  }

  Future<void> _fetchTotalMaterialCount() async {
    setState(() {
      isLoading = true;
    });

    try {
      final locationIdResponse = await http.get(Uri.parse(
          'http://10.10.208.115:8083/api/location/${widget.username}'));
      final locationId = json.decode(locationIdResponse.body);

      final String apiUrl = widget.isPersonelBazli
          ? 'http://10.10.208.115:8083/api/materials/personel-total/$locationId'
          : 'http://10.10.208.115:8083/api/materials/location/$locationId/total';

      final response = await http.get(Uri.parse(apiUrl));
      final data = json.decode(response.body);

      setState(() {
        totalMaterials =
            data is int ? data : int.parse(data['total'].toString());
      });
    } catch (e) {
      print('Error fetching total material count: $e');
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  void _logout() {
    Navigator.pushAndRemoveUntil(
      context,
      MaterialPageRoute(builder: (context) => MyApp()),
      (Route<dynamic> route) => false,
    );
  }

  Future<void> _selectYear(BuildContext context) async {
    int? pickedYear = await showDialog<int>(
      context: context,
      builder: (BuildContext context) {
        return YearPickerDialog(
          initialYear: DateTime.now().year,
          minYear: 2024, // 2024'ten önceki yıllar kaldırıldı
          maxYear: DateTime.now().year + 2, // Gelecek yıl dahil edildi
        );
      },
    );

    if (pickedYear != null) {
      setState(() {
        selectedYear = pickedYear;
      });
    }
  }

  void _showSaveConfirmation() {
    if (selectedYear == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Lütfen bir yıl seçin.')),
      );
      return;
    }

    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => SayimEkrani(
          username: widget.username,
          selectedYear: selectedYear.toString(),
        ),
      ),
    );
  }

  void _showErrorMessage() {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Lütfen bir yıl seçin.')),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
        preferredSize: const Size.fromHeight(120.0),
        child: AppBar(
          centerTitle: true,
          backgroundColor: Colors.white,
          flexibleSpace: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Image.asset(
                  'lib/images/botasarkaplansız.png',
                  height: 100, // Logoyu büyüttük
                ),
                const SizedBox(height: 0),
                const Text(
                  'Envanter Takip Sistemi',
                  style: TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.bold,
                      color: Colors.black),
                ),
              ],
            ),
          ),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: <Widget>[
            const SizedBox(height: 2),
            ElevatedButton(
              onPressed: () => _selectYear(context),
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 204, 44, 44),
                padding:
                    const EdgeInsets.symmetric(vertical: 15, horizontal: 30),
                textStyle: const TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                ),
              ),
              child: Text(
                selectedYear == null ? 'Yıl Seçiniz' : '$selectedYear',
                style: const TextStyle(color: Colors.white),
              ),
            ),
            const SizedBox(height: 10),
            Container(
              width: double.infinity,
              height: 90,
              margin: const EdgeInsets.only(bottom: 20.0),
              padding: const EdgeInsets.all(8.0),
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(16.0),
                gradient: const LinearGradient(
                  colors: [
                    Color(0xFFFF4A4A), // Kırmızı
                    Color(0xFF8B0000), // Koyu kırmızı
                  ],
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                ),
                boxShadow: [
                  BoxShadow(
                    color: Colors.black.withOpacity(0.2),
                    spreadRadius: 2,
                    blurRadius: 10,
                    offset: const Offset(0, 3),
                  ),
                ],
              ),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Text(
                    '$totalMaterials',
                    style: const TextStyle(
                      fontSize: 24.0,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                  const SizedBox(height: 5.0),
                  const Text(
                    'Toplam Malzeme Sayısı',
                    style: TextStyle(
                      fontSize: 18.0,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                ],
              ),
            ),
            Expanded(
              child: GridView.count(
                crossAxisCount: 2,
                childAspectRatio: 1,
                crossAxisSpacing: 10.0,
                mainAxisSpacing: 10.0,
                children: <Widget>[
                  buildModernButton(context, 'SAYIM', () {
                    if (selectedYear == null) {
                      _showErrorMessage();
                    } else {
                      _showSaveConfirmation();
                    }
                  }),
                  buildModernButton(context, 'MALZEME BUL', () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => MalzemeBul()),
                    );
                  }),
                  buildModernButton(context,
                      isLoading ? 'GÜNCELLENİYOR...' : 'VERİLERİ GÜNCELLE', () {
                    if (selectedYear == null) {
                      _showErrorMessage();
                    } else if (!isLoading) {
                      _fetchTotalMaterialCount();
                    }
                  }),
                  buildModernButton(context, 'ÇIKIŞ YAP', _logout),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildModernButton(
      BuildContext context, String text, VoidCallback? onPressed) {
    return ElevatedButton(
      style: ElevatedButton.styleFrom(
        backgroundColor: const Color.fromARGB(255, 204, 44, 44),
        minimumSize: const Size(60, 60),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(16.0),
        ),
        elevation: 10,
        shadowColor: const Color.fromARGB(255, 196, 35, 35).withOpacity(0.5),
      ),
      onPressed: onPressed,
      child: Text(
        text,
        textAlign: TextAlign.center,
        style: const TextStyle(
          color: Colors.white,
          fontSize: 18,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }
}

class YearPickerDialog extends StatelessWidget {
  final int initialYear;
  final int minYear;
  final int maxYear;

  YearPickerDialog({
    required this.initialYear,
    required this.minYear,
    required this.maxYear,
  });

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('Yıl Seçiniz'),
      content: Container(
        width: double.minPositive,
        height: 300,
        child: YearPicker(
          firstDate: DateTime(minYear),
          lastDate: DateTime(maxYear),
          initialDate: DateTime(initialYear),
          selectedDate: DateTime(initialYear),
          onChanged: (DateTime dateTime) {
            if (dateTime.year >= 2024 && dateTime.year <= maxYear) {
              Navigator.pop(context, dateTime.year);
            }
          },
        ),
      ),
    );
  }
}
