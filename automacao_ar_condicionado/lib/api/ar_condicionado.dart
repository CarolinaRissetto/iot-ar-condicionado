import 'dart:convert';

import 'package:automacao_ar_condicionado/api/config.dart';
import 'package:http/http.dart';

class Operacao {
  static const ligar = 'LIGAR';
  static const desligar = 'DESLIGAR';
  static const alterarTemperatura = 'ALTERAR_TEMPERATURA';
}

class ArCondicionadoRequest {
  final int? temperatura;
  final String operacao;

  ArCondicionadoRequest({required this.operacao, this.temperatura});

  String toJson() {
    return jsonEncode({'temperatura': temperatura, 'operacao': operacao});
  }
}

class AgendamentoRequest {
  final String horaLigamento;
  final String horaDesligamento;

  AgendamentoRequest(
      {required this.horaLigamento, required this.horaDesligamento});

  String toJson() {
    return jsonEncode(
        {'horaLigamento': horaLigamento, 'horaDesligamento': horaDesligamento});
  }
}

class ObterAgendamentoRequest {
  final String horaLigamento;
  final String horaDesligamento;

  ObterAgendamentoRequest(
      {required this.horaLigamento, required this.horaDesligamento});

  factory ObterAgendamentoRequest.fromJson(Map<String, dynamic> json) {
    return ObterAgendamentoRequest(
      horaLigamento: json['horaLigamento'],
      horaDesligamento: json['horaDesligamento'],
    );
  }
}

class ArConcidionadoService {
  final String token;
  final url = '${ApiConfig.server}/ar-condicionado';

  ArConcidionadoService(this.token);

  Future<bool> ligar() async {
    return _operacao(ArCondicionadoRequest(operacao: Operacao.ligar));
  }

  Future<bool> desligar() async {
    return _operacao(ArCondicionadoRequest(operacao: Operacao.desligar));
  }

  Future<bool> alterarTemperatura(int temperatura) async {
    return _operacao(ArCondicionadoRequest(
        operacao: Operacao.alterarTemperatura, temperatura: temperatura));
  }

  Future<bool> agendar(String horaLigamento, String horaDesligamento) async {
    final response = await post(Uri.parse('$url/agendamento'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token'
        },
        body: AgendamentoRequest(
                horaLigamento: horaLigamento,
                horaDesligamento: horaDesligamento)
            .toJson());

    return response.statusCode == 200;
  }

  Future<bool> deletarAgendamento() async {
    final response = await delete(Uri.parse('$url/agendamento'), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });

    return response.statusCode == 200;
  }

  Future<ObterAgendamentoRequest?> obterAgendamento() async {
    final response = await get(Uri.parse('$url/agendamento'), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });

    if (response.statusCode == 200 && response.body.isNotEmpty) {
      return ObterAgendamentoRequest.fromJson(jsonDecode(response.body));
    }

    return null;
  }

  Future<bool> _operacao(ArCondicionadoRequest request) async {
    final response = await post(Uri.parse('$url/operacao'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token'
        },
        body: request.toJson());

    return response.statusCode == 200;
  }
}
