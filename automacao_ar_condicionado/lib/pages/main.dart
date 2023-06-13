import 'dart:developer';

import 'package:automacao_ar_condicionado/api/ar_condicionado.dart';
import 'package:automacao_ar_condicionado/context/agendamento.dart';
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
  Future<ObterAgendamentoRequest?> obtendoAgendamento = Future.value(null);

  @override
  void initState() {
    final service =
        ArConcidionadoService(Provider.of<Auth>(context, listen: false).token!);
    _service = service;

    obtendoAgendamento = service.obterAgendamento();
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

  Future _onDelete() async {
    await _service!.deletarAgendamento();

    setState(() {
      obtendoAgendamento = _service!.obterAgendamento();
    });
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

    setState(() {
      obtendoAgendamento = _service!.obterAgendamento();
    });
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
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Spacer(
              flex: 1,
            ),
            Column(children: [
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
                    ],
                  ),
                ),
              ),
            ]),
            const Spacer(
              flex: 1,
            ),
            Expanded(
              flex: 0,
              child: Padding(
                padding: const EdgeInsets.all(12.0),
                child: FutureBuilder(
                  future: obtendoAgendamento,
                  builder: (ctx, estadoAtual) => Row(
                    children: [
                      !estadoAtual.hasData
                          ? const Text(
                              'Sem agendamentos.',
                            )
                          : Column(
                              children: [
                                Text(
                                    'Ligar ás ${estadoAtual.data!.horaLigamento} horas'),
                                Text(
                                    'Desligar ás ${estadoAtual.data!.horaDesligamento} horas')
                              ],
                            ),
                      Container(
                        child: !estadoAtual.hasData
                            ? null
                            : IconButton(
                                onPressed: _onDelete,
                                icon: const Icon(Icons.delete)),
                      )
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
