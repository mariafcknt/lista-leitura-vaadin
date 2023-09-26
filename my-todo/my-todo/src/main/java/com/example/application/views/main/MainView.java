package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.H1;

import java.time.LocalDate;

@Route("livros")
public class MainView extends VerticalLayout {

    private TextField nomeLivro = new TextField("Nome do Livro");
    private TextField autorLivro = new TextField("Autor do Livro");
    private Checkbox terminouLeitura = new Checkbox("Já terminou de ler o livro?");
    private DatePicker dataConclusao = new DatePicker("Data de Conclusão de Leitura");
    private TextField avaliacao = new TextField("Avaliação (de 1 a 5)");
    private Button adicionarLivro = new Button("Adicionar Livro");

    private Grid<Livro> grid = new Grid<>(Livro.class);

    public MainView() {
        // Configurações iniciais
        setSpacing(true);
        setClassName("main-view");

        // Adiciona o título "Adicione seus livros!" usando o elemento H1
        add(new H1("Adicione seus livros!"));

        // Configura o evento de alteração da checkbox "terminouLeitura"
        terminouLeitura.addValueChangeListener(event -> toggleCamposConclusao());

        // Inicialmente, oculta os campos de data de conclusão e avaliação
        dataConclusao.setVisible(false);
        avaliacao.setVisible(false);

        // Configura o botão "Adicionar Livro"
        adicionarLivro.addClickListener(event -> adicionarLivro());

        // Configura a tabela
        grid.setColumns("nome", "autor", "terminouLeitura", "dataConclusao", "avaliacao");

        // Adiciona componentes ao layout
        add(nomeLivro, autorLivro, terminouLeitura, dataConclusao, avaliacao, adicionarLivro, grid);
    }

    private void toggleCamposConclusao() {
        dataConclusao.setVisible(terminouLeitura.getValue());
        avaliacao.setVisible(terminouLeitura.getValue());
    }

    private void adicionarLivro() {
        Livro livro = new Livro(
            nomeLivro.getValue(),
            autorLivro.getValue(),
            terminouLeitura.getValue(),
            dataConclusao.getValue(),
            avaliacao.getValue()
        );

        grid.setItems(livro); // Use setItems para adicionar o livro à tabela

        // Limpa os campos após adicionar o livro
        nomeLivro.clear();
        autorLivro.clear();
        terminouLeitura.clear();
        dataConclusao.clear();
        avaliacao.clear();
    }

    public static class Livro {
        private String nome;
        private String autor;
        private boolean terminouLeitura;
        private LocalDate dataConclusao;
        private String avaliacao;

        public Livro(String nome, String autor, boolean terminouLeitura, LocalDate dataConclusao, String avaliacao) {
            this.nome = nome;
            this.autor = autor;
            this.terminouLeitura = terminouLeitura;
            this.dataConclusao = dataConclusao;
            this.avaliacao = avaliacao;
        }

        public String getNome() {
            return nome;
        }

        public String getAutor() {
            return autor;
        }

        public boolean isTerminouLeitura() {
            return terminouLeitura;
        }

        public LocalDate getDataConclusao() {
            return dataConclusao;
        }

        public String getAvaliacao() {
            return avaliacao;
        }
    }
}
