package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route("livros")
public class MainView extends VerticalLayout {

    private TextField nomeLivro = new TextField("Nome do Livro");
    private TextField autorLivro = new TextField("Autor do Livro");
    private Checkbox terminouLeitura = new Checkbox("Já terminou de ler o livro?");
    private DatePicker dataConclusao = new DatePicker("Data de Conclusão de Leitura");
    private TextField avaliacao = new TextField("Avaliação (de 1 a 5)");
    private Button adicionarLivro = new Button("Adicionar Livro");
    private Button editarLivro = new Button("Salvar Edição");
    private Button excluirLivro = new Button("Excluir Livro");

    private Grid<Livro> grid = new Grid<>(Livro.class);
    private Livro livroEmEdicao = null;

    private List<Livro> livros = new ArrayList<>();

    public MainView() {
        // Configurações iniciais
        setSpacing(true);
        setClassName("main-view");

        // Defina o estilo de fundo para azul claro
        //getStyle().set("background-color", "lightblue");

       // Configura o evento de alteração da checkbox "terminouLeitura"
        terminouLeitura.addValueChangeListener(event -> toggleCamposConclusao());

        // Inicialmente, oculta os campos de data de conclusão e avaliação
        dataConclusao.setVisible(false);
        avaliacao.setVisible(false);

        // Configura o botão "Adicionar Livro" com o ícone
        adicionarLivro.setIcon(VaadinIcon.PLUS.create());
        adicionarLivro.addClickListener(event -> adicionarLivro());

        // Adiciona o título "Adicione seus livros!" usando o elemento H1
        add(new H1("Adicione seus livros!"));

        add(new Paragraph("Após ter adicionado o livro, caso queira editar as informações dele, selecione-o na tabela, faça a edição e depois clique em \"Salvar Edição\". Caso queira excluí-lo, selecione-o na tabela e clique em \"Excluir Livro\"."));
        
        // Configura o evento de alteração da checkbox "terminouLeitura"
        terminouLeitura.addValueChangeListener(event -> toggleCamposConclusao());

        // Inicialmente, oculta os campos de data de conclusão e avaliação
        dataConclusao.setVisible(false);
        avaliacao.setVisible(false);

        // Configura o botão "Adicionar Livro"
        adicionarLivro.addClickListener(event -> adicionarLivro());

        // Configura os botões de edição e exclusão
        editarLivro.setEnabled(false);
        editarLivro.addClickListener(event -> salvarEdicao());
        excluirLivro.setEnabled(false);
        excluirLivro.addClickListener(event -> excluirLivro());

        // Configura a grade
        grid.setColumns("nome", "autor", "terminouLeitura", "dataConclusao", "avaliacao");
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.asSingleSelect().addValueChangeListener(event -> editarLivro(event.getValue()));
        grid.setItems(livros);

        // Adiciona componentes ao layout
        add(nomeLivro, autorLivro, terminouLeitura, dataConclusao, avaliacao, adicionarLivro, editarLivro, excluirLivro, grid);
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

        livros.add(livro);
        grid.setItems(livros);

        // Limpa os campos após adicionar o livro
        limparCampos();
    }

    private void editarLivro(Livro livro) {
        if (livro != null) {
            livroEmEdicao = livro;
            nomeLivro.setValue(livro.getNome());
            autorLivro.setValue(livro.getAutor());
            terminouLeitura.setValue(livro.isTerminouLeitura());
            dataConclusao.setValue(livro.getDataConclusao());
            avaliacao.setValue(livro.getAvaliacao());

            editarLivro.setEnabled(true);
            excluirLivro.setEnabled(true);
        } else {
            limparCampos();
        }
    }

    private void salvarEdicao() {
        if (livroEmEdicao != null) {
            livroEmEdicao.setNome(nomeLivro.getValue());
            livroEmEdicao.setAutor(autorLivro.getValue());
            livroEmEdicao.setTerminouLeitura(terminouLeitura.getValue());
            livroEmEdicao.setDataConclusao(dataConclusao.getValue());
            livroEmEdicao.setAvaliacao(avaliacao.getValue());

            grid.getDataProvider().refreshAll();

            limparCampos();
        }
    }

    private void excluirLivro() {
        if (livroEmEdicao != null) {
            livros.remove(livroEmEdicao);
            grid.setItems(livros);

            limparCampos();
        }
    }

    private void limparCampos() {
        livroEmEdicao = null;
        nomeLivro.clear();
        autorLivro.clear();
        terminouLeitura.clear();
        dataConclusao.clear();
        avaliacao.clear();
        editarLivro.setEnabled(false);
        excluirLivro.setEnabled(false);
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

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public boolean isTerminouLeitura() {
            return terminouLeitura;
        }

        public void setTerminouLeitura(boolean terminouLeitura) {
            this.terminouLeitura = terminouLeitura;
        }

        public LocalDate getDataConclusao() {
            return dataConclusao;
        }

        public void setDataConclusao(LocalDate dataConclusao) {
            this.dataConclusao = dataConclusao;
        }

        public String getAvaliacao() {
            return avaliacao;
        }

        public void setAvaliacao(String avaliacao) {
            this.avaliacao = avaliacao;
        }
    }
}

