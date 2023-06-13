import 'dart:developer';

import 'package:automacao_ar_condicionado/api/ar_condicionado.dart';
import 'package:automacao_ar_condicionado/context/auth.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  ArConcidionadoService? _service;
  bool _isOn = false;
  int _temperature = 20;
  String? _scheduledTime;
  String? _scheduleTurnOffTime;

  @override
  void initState() {
    _service =
        ArConcidionadoService(Provider.of<Auth>(context, listen: false).token!);
    super.initState();
  }

  void _togglePower() async {
    _isOn ? await _service!.desligar() : await _service!.ligar();

    setState(() {
      _isOn = !_isOn;
    });
  }

  void _incrementTemperature() async {
    if (_temperature >= 21) return;

    setState(() {
      _temperature++;
    });

    await _service!.alterarTemperatura(_temperature);
  }

  void _decrementTemperature() async {
    if (_temperature <= 18) return;

    setState(() {
      _temperature--;
    });

    await _service!.alterarTemperatura(_temperature);
  }

  void showMessage(String text, {Color? color = Colors.red}) {
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
      content: Text(text),
      duration: const Duration(seconds: 2),
      backgroundColor: color,
    ));
  }

  Future<TimeOfDay?> getScheduleTime(String text) async {
    return await showTimePicker(
      helpText: text,
      context: context,
      initialTime: TimeOfDay.now(),
      builder: (BuildContext context, Widget? child) {
        return MediaQuery(
          data: MediaQuery.of(context).copyWith(alwaysUse24HourFormat: false),
          child: child!,
        );
      },
    );
  }

  bool validateTime(TimeOfDay? time) {
    if (time == null) {
      showMessage('Nenhum horário foi selecionado.');
      return false;
    }

    return true;
  }

  Future<void> _setScheduledTime() async {
    var selected = await getScheduleTime('Horário para ligar o aparelho.');

    if (!validateTime(selected)) return;

    var selectedTurnOff =
        await getScheduleTime('Horário para desligar o aparelho.');

    if (!validateTime(selectedTurnOff)) return;

    setState(() {
      _scheduledTime = selected!.format(context);
      _scheduleTurnOffTime = selectedTurnOff!.format(context);
    });

    await _service!.agendar(_scheduledTime!, _scheduleTurnOffTime!);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.purple.withOpacity(0.5),
        title: Text(widget.title),
        automaticallyImplyLeading: false,
      ),
      floatingActionButton: FloatingActionButton(
        shape: const CircleBorder(),
        onPressed: _setScheduledTime,
        tooltip: 'Agendar',
        child: const Icon(Icons.schedule),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Temperatura: $_temperature°C',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 16),
            Align(
              alignment: Alignment.center,
              child: Container(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    FloatingActionButton(
                      heroTag: "ligar",
                      onPressed: _togglePower,
                      tooltip: _isOn ? 'Desligar' : 'Ligar',
                      child: Icon(_isOn
                          ? Icons.power_settings_new
                          : Icons.power_settings_new_outlined),
                    ),
                    const SizedBox(height: 16),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        FloatingActionButton(
                          heroTag: "Diminuir",
                          onPressed: _decrementTemperature,
                          tooltip: 'Diminuir temperatura',
                          child: const Icon(Icons.remove),
                        ),
                        const SizedBox(width: 16),
                        FloatingActionButton(
                          heroTag: "Aumentar",
                          onPressed: _incrementTemperature,
                          tooltip: 'Aumentar temperatura',
                          child: const Icon(Icons.add),
                        ),
                      ],
                    ),
                    const SizedBox(height: 16),
                    Row(
                      children: [
                        _scheduledTime == null
                            ? const Text(
                                'Sem agendamentos.',
                              )
                            : Column(
                                children: [
                                  Text('Ligar ás $_scheduledTime horas'),
                                  Text(
                                      'Desligar ás $_scheduleTurnOffTime horas')
                                ],
                              ),
                        Container(
                          child: _scheduledTime == null
                              ? null
                              : IconButton(
                                  onPressed: () {
                                    setState(() {
                                      _scheduledTime = null;
                                    });
                                  },
                                  icon: const Icon(Icons.delete)),
                        )
                      ],
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
