import {Component, OnInit} from '@angular/core';
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
export class ListarCidades implements OnInit {
    listaCidades: CityDTO[] = [];
    carregando: boolean = false;
    cidadeSelecionada: CityDTO = new CityDTO();
    mostraJanelaCadastro: boolean = false;

    constructor(private service: ProjetoService, private messageService: MessageService) {}

    ngOnInit(): void {
        this.pesquisarCidades();
    }

    private pesquisarCidades(): void {
        this.carregando = true;
        this.service.pesquisarCidades().subscribe({
            next: (cidades) => {
                this.listaCidades = cidades;
                this.carregando = false;
            },
            error: () => {
                this.carregando = false;
                this.messageService.add({
                    severity: 'error',
                    summary: 'Erro',
                    detail: 'Não foi possível carregar as cidades do backend.'
                });
            }
        });
    }

    public abreJanelaParaCadastrarNovaCidade() {
        this.cidadeSelecionada = new CityDTO();
        this.cidadeSelecionada.capital = false;
        this.mostraJanelaCadastro = true;
    }

    public abreJanelaParaAlterarCidade(cidade: CityDTO): void {
        this.cidadeSelecionada = new CityDTO();
        this.cidadeSelecionada.id = cidade.id;
        this.cidadeSelecionada.nome = cidade.nome;
        this.cidadeSelecionada.uf = cidade.uf;
        this.cidadeSelecionada.capital = cidade.capital ?? false;
        this.mostraJanelaCadastro = true;
    }

    public excluir(cidade: CityDTO) {
        this.service.excluir(cidade).subscribe({
            next: () => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Info',
                    detail: `Cidade '${cidade.nome}' excluída com sucesso!`
                });
                this.pesquisarCidades();
            },
            error: () => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Erro',
                    detail: `Não foi possível excluir a cidade '${cidade.nome}'. Verifique se há comércios vinculados.`
                });
            }
        });
    }

    public fechaJanelaCadastro(salvou: boolean): void {
        this.mostraJanelaCadastro = false;

        if (salvou) {
            this.messageService.add({ severity: 'success', summary: 'Info', detail: 'Cidade salva com sucesso!' });
            this.pesquisarCidades();
        }
    }

    public formatarCapital(capital?: boolean): string {
        return capital ? 'Sim' : 'Não';
    }
}
