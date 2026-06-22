import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ImportsModule } from './imports';
import { CityDTO } from '@domain/city-dto';
import { ProjetoService } from '@service/projeto-service';
import { MessageService } from 'primeng/api';

//-------------------------------------------------------------------------------------
/** Tela para cadastro de cidades */
//-------------------------------------------------------------------------------------
@Component({
    selector: 'cadastrar-cidade',
    templateUrl: 'cadastrar-cidade.html',
    standalone: true,
    imports: [ImportsModule]
})
export class CadastrarCidade {

    //-------------------------------------------------------
    // Parâmetro de entrada para o componente
    //-------------------------------------------------------
    @Input() public cidade: CityDTO = new CityDTO();

    //-------------------------------------------------------
    // Evento lançado ao fechar a janela
    //-------------------------------------------------------
    @Output('onClose') private eventoFechaJanela = new EventEmitter<boolean>();

    salvando: boolean = false;

    //--------------------------------------------------------------
    /** Construtor. */
    //--------------------------------------------------------------
    constructor(
        private service: ProjetoService,
        private messageService: MessageService
    ) {}

    get tituloDialog(): string {
        return this.cidade.id ? 'Alterar Cidade' : 'Cadastrar Cidade';
    }

    //-------------------------------------------------------------------------------------
    /** Método chamado ao clicar no botao 'salvar' */
    //-------------------------------------------------------------------------------------
    public salvar(): void {
        if (!this.cidade.nome?.trim()) {
            this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Informe o nome da cidade.' });
            return;
        }

        if (!this.cidade.uf || this.cidade.uf.trim().length !== 2) {
            this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Informe a UF com 2 caracteres.' });
            return;
        }

        if (this.cidade.capital === undefined || this.cidade.capital === null) {
            this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Informe se a cidade é capital.' });
            return;
        }

        this.salvando = true;
        this.service.salvar(this.cidade).subscribe({
            next: () => {
                this.salvando = false;
                this.eventoFechaJanela.emit(true);
            },
            error: () => {
                this.salvando = false;
                this.messageService.add({
                    severity: 'error',
                    summary: 'Erro',
                    detail: 'Não foi possível salvar a cidade.'
                });
            }
        });
    }

    //-------------------------------------------------------------------------------------
    /** Método chamado ao clicar no botao 'cancelar' */
    //-------------------------------------------------------------------------------------
    public cancelar(): void {
        this.eventoFechaJanela.emit(false);
    }

}
