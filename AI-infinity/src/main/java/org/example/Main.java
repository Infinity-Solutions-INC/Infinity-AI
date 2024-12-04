package org.example;

public class Main {


    public static void main(String[] args) {
        QuerysBD querysBD = new QuerysBD();
        IA ia = new IA();



        try {
//          Obter o prompt do banco
            String taxaEvasao = querysBD.obterTaxaEvasão(100);
            String prompt = querysBD.obterPromptPorId(1);
            prompt = prompt.replace("<X>", taxaEvasao);
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
