import 'package:automacao_ar_condicionado/api/ar_condicionado.dart';
import 'package:flutter/material.dart';

class Agendamento with ChangeNotifier {
  final String token;
  String? horaLigamento;
  String? horaDesligamento;

  Agendamento(this.token);

  Future obter() async {
    final response = await ArConcidionadoService(token).obterAgendamento();

    if (response != null) {
      horaLigamento = response.horaLigamento;
      horaDesligamento = response.horaDesligamento;

      notifyListeners();
    }
  }
}
