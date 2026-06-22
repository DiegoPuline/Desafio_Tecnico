import {Component} from '@angular/core';
import {ImportsModule} from './imports';
import {CityDTO} from '@domain/city-dto';
import {ProjetoService} from '@service/projeto-service';
import {CadastrarCidade} from './cadastrar-cidade';
import {MessageService} from 'primeng/api';

//-------------------------------------------------------------------------------------
/** Tela para listar cidades */
//-------------------------------------------------------------------------------------
@Component({
    selector: 'listar-cidades',
    templateUrl: 'listar-cidades.html',
    standalone: true,
    imports: [ImportsModule, CadastrarCidade],
    providers: [ProjetoService, MessageService]
})
export class ListarCidades {
    //-------------------------------------------------------
    // Lista de cidades, exibida na tabela
    //-------------------------------------------------------
    listaCidades: CityDTO[] = [];

    //-------------------------------------------------------------
    // Atributo que guarda a cidade que foi selecionada na tabela
    //-------------------------------------------------------------
    cidadeSelecionada: CityDTO = new CityDTO();

    //-------------------------------------------------------------
    // Flag usada para mostrar/esconder a janela de cadastro
    //-------------------------------------------------------------
    mostraJanelaCadastro: boolean = false;

    //--------------------------------------------------------------
    /** Construtor. Recebe os services usados pelo componente */
    //--------------------------------------------------------------
    constructor(private service: ProjetoService, private messageService: MessageService) {}

    //-------------------------------------------------------------------------------------
    /** Inicializacao do componente. Recupera a lista de cidades para exibir na tabela */
    //-------------------------------------------------------------------------------------
    ngOnInit() {
        this.pesquisarCidades();
    }

    //-------------------------------------------------------------------------------------
    /** Método chamado para recuperar cidades para a tabela */
    //-------------------------------------------------------------------------------------
    private pesquisarCidades(): void {
        this.service.pesquisarCidades().subscribe((cidades) => {
            this.listaCidades = cidades;
        });
    }

    //-------------------------------------------------------------------------------------
    /** Método chamado ao clicar no botão 'Nova Cidade' */
    //-------------------------------------------------------------------------------------
    public abreJanelaParaCadastrarNovaCidade() {
        // Define a cidade selecionada como uma nova cidade
        this.cidadeSelecionada = new CityDTO();

        // Exibe a janela de cadastro
        this.mostraJanelaCadastro = true;
    }

    //-------------------------------------------------------------------------------------
    /** Método chamado ao clicar no botão 'Alterar' */
    //-------------------------------------------------------------------------------------
    public abreJanelaParaAlterarCidade(cidade: CityDTO): void {
        // Copia os dados da cidade selecionada para uma nova cidade
        this.cidadeSelecionada = new CityDTO();
        this.cidadeSelecionada.id = cidade.id;
        this.cidadeSelecionada.nome = cidade.nome;
        this.cidadeSelecionada.uf = cidade.uf;
        this.cidadeSelecionada.capital = cidade.capital;

        // Exibe a janela de cadastro
        this.mostraJanelaCadastro = true;
    }

    //-------------------------------------------------------------------------------------
    /** Método chamado ao clicar no botão 'Excluir' */
    //-------------------------------------------------------------------------------------
    public excluir(cidade: CityDTO) {
        // Chama o backend para excluir a cidade selecionada
        this.service.excluir(cidade).subscribe((retorno) => {
            this.messageService.add({ severity: 'success', summary: 'Info', detail: `Cidade '${cidade.nome}' excluída com sucesso!` });

            // Atualiza a lista de cidades
            setTimeout(() => this.pesquisarCidades(), 100);
        }) ;
    }

    //-------------------------------------------------------------------------------------
    /** Método chamado quando a janela de cadastro é fechada */
    //-------------------------------------------------------------------------------------
    public fechaJanelaCadastro(salvou: boolean): void {
        // Esconde a janela de cadastro
        this.mostraJanelaCadastro = false;

        // Se salvou, atualiza a lista de cidades
        if(salvou) {
           this.messageService.add({ severity: 'success', summary: 'Info', detail: 'Cidade salva com sucesso!' });
           setTimeout(() => this.pesquisarCidades(), 100);
        }
    }

}
