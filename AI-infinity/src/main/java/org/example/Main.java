package org.example;

public class Main {


    public static void main(String[] args) {
        QuerysBD querysBD = new QuerysBD();
        IA ia = new IA();



        try {
            String idIntituicao = System.getenv("ID_INSTITUICAO");
//            String idIntituicao = "100";
//          Obter o prompt do banco
            String qtdTurmasperiodoManhaIntegral = querysBD.obterQtdTurmaMeV(idIntituicao);
            String qtdMensalidadesAltas = querysBD.obterMensalidadeGrande(idIntituicao);
            String qtdTurmasExatas = querysBD.obterQtdTurmasExatas(idIntituicao);
            String qtdTurmasSaude = querysBD.obterQtdTurmasSaude(idIntituicao);
            String qtdTurmasCienciasSociais = querysBD.obterQtdTurmasCienciasSociais(idIntituicao);
            String prompt = querysBD.obterPromptPorId(1);
            prompt = prompt.replace("<x1>", qtdTurmasperiodoManhaIntegral);
            prompt = prompt.replace("<x2>", qtdMensalidadesAltas);
            prompt = prompt.replace("<x3>", qtdTurmasExatas);
            prompt = prompt.replace("<x4>", qtdTurmasSaude);
            prompt = prompt.replace("<x5>", qtdTurmasCienciasSociais);
            System.out.println("Prompt: " + prompt);

            // Obter resposta da API
            String response = ia.getCompletion(prompt);
            System.out.println("Resposta da API: " + response);
            // Salvar resposta no banco
            querysBD.salvarResposta(prompt, response);
            System.out.println("Resposta salva com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
