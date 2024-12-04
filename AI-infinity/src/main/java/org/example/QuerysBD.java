package org.example;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Date;
import java.util.List;

public class QuerysBD {
    // Configuração do DataSource e do JdbcTemplate
    private DriverManagerDataSource dataSource;
    private JdbcTemplate jdbcTemplate;



    public QuerysBD() {
        dataSource = new DriverManagerDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost:3306/infinity_solutions?useSSL=false&serverTimezone=UTC");
        dataSource.setUrl("jdbc:mysql://mysql-app:3306/infinity_solutions?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("010652");
        dataSource.setPassword(System.getenv("DB_PASSWORD"));
        dataSource.setUsername(System.getenv("DB_USER"));
        jdbcTemplate = new JdbcTemplate(dataSource);
    }




    // Inserir um prompt na tabela
    public void inserirPrompt(String prompt) {
        jdbcTemplate.update("""
                INSERT INTO prompt_ia (descricao_prompt)
                VALUES (?)
                """, prompt);
    }

    // Obter um prompt pelo ID
    public String obterPromptPorId(int id) {
        return jdbcTemplate.queryForObject("""
                SELECT descricao_prompt FROM prompt_ia WHERE codigo_prompt = ?
                """, new Object[]{id}, String.class);
    }

//    public String obterTaxaEvasão(String id) {
//        return jdbcTemplate.queryForObject("""
//                SELECT ROUND((SUM(t.qtd_ingressantes) - SUM(t.qtd_alunos_permanencia)) * 100.0 / SUM(t.qtd_ingressantes),2)
//                FROM turma t
//                INNER JOIN curso c ON t.fkcodigo_curso = c.codigo_curso
//                INNER JOIN instituicao inst ON c.fkcodigo_instituicao = inst.codigo_instituicao
//                WHERE inst.codigo_instituicao = ?;
//                """, new Object[]{id}, String.class);
//    }
public String obterQtdTurmaMeV(String id) {
    return jdbcTemplate.queryForObject("""
            select count(turma.codigo_turma) from turma
            inner join curso on curso.codigo_curso = turma.fkcodigo_curso
            inner join instituicao on instituicao.codigo_instituicao = curso.fkcodigo_instituicao
            where instituicao.codigo_instituicao = ? and turma.turno_turma in('Matutino', 'Vespertino');
                """, new Object[]{id}, String.class);
}
    public String obterMensalidadeGrande(String id) {
        return jdbcTemplate.queryForObject("""
                select count(turma.codigo_turma) from turma
                inner join curso on curso.codigo_curso = turma.fkcodigo_curso
                inner join instituicao on instituicao.codigo_instituicao = curso.fkcodigo_instituicao
                where instituicao.codigo_instituicao =  ? and turma.mensalidade_turma > 927.00;
                
                """, new Object[]{id}, String.class);
    }
    public String obterQtdTurmasExatas(String id) {
        return jdbcTemplate.queryForObject("""
                select count(turma.codigo_turma) from turma
                inner join curso on curso.codigo_curso = turma.fkcodigo_curso
                inner join instituicao on instituicao.codigo_instituicao = curso.fkcodigo_instituicao
                inner join area_curso on area_curso.codigo_area = curso.fkcodigo_area
                where instituicao.codigo_instituicao =  ? and area_curso.nome_area  = 'Ciências  naturais, matemática e estatística';
                """, new Object[]{id}, String.class);
    }
    public String obterQtdTurmasSaude(String id) {
        return jdbcTemplate.queryForObject("""
                select count(turma.codigo_turma) from turma
                inner join curso on curso.codigo_curso = turma.fkcodigo_curso
                inner join instituicao on instituicao.codigo_instituicao = curso.fkcodigo_instituicao
                inner join area_curso on area_curso.codigo_area = curso.fkcodigo_area
                where instituicao.codigo_instituicao =  ? and area_curso.nome_area  = 'Saúde e bem estar';
                """, new Object[]{id}, String.class);
    }
    public String obterQtdTurmasCienciasSociais(String id) {
        return jdbcTemplate.queryForObject("""
                select count(turma.codigo_turma) from turma
                inner join curso on curso.codigo_curso = turma.fkcodigo_curso
                inner join instituicao on instituicao.codigo_instituicao = curso.fkcodigo_instituicao
                inner join area_curso on area_curso.codigo_area = curso.fkcodigo_area
                where instituicao.codigo_instituicao =  ? and area_curso.nome_area  = 'Ciências sociais, comunicação e informação';
                """, new Object[]{id}, String.class);
    }

    // Salvar uma resposta no banco
    public void salvarResposta(String prompt, String response) {
        jdbcTemplate.update("""
                INSERT INTO recomendacao_recebida (fkcodigo_instituicao, fkcodigo_prompt, descricao_recomendacao_recebida, dt_hr_recomendacao_recebida)
                VALUES (100, 1, ?, CURRENT_TIMESTAMP)
                """, response);
    }


}
