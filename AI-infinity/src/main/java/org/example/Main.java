package org.example;

public class Main {


    public static void main(String[] args) {
        QuerysBD querysBD = new QuerysBD();
        IA ia = new IA();



        try {
//          inserir prompt no banco
            querysBD.inserirPrompt("A taxa de evasão da universidade está atualmente em <X>%, e buscamos estratégias para reduzi-la de forma significativa. Considere os seguintes aspectos:\n" +
                    "Engajamento Estudantil: Proponha formas de melhorar a integração dos alunos, suporte emocional e engajamento acadêmico.\n" +
                    "Infraestrutura e Recursos: Analise como a melhoria das instalações e recursos tecnológicos podem impactar na retenção dos alunos.\n" +
                    "Flexibilidade Acadêmica: Sugira ajustes nos horários, modalidades de ensino (presencial, EaD, híbrido) e métodos avaliativos para atender às diversas necessidades dos estudantes.\n" +
                    "Suporte Financeiro: Inclua ideias para facilitar o acesso a bolsas, descontos ou financiamento estudantil que aliviem o peso financeiro sobre os alunos.\n" +
                    "Comunicação: Indique como estabelecer uma comunicação mais próxima e eficaz entre alunos e a gestão universitária para identificar e resolver problemas rapidamente.\n" +
                    "As recomendações devem ser práticas, escaláveis e baseadas em melhores práticas de gestão educacional.\n" +
                    "Considere valores até 40% como baixa evasão, valores até 60% como media evasão, e valores ate 100% como alta evasão");

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
