import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CityDTO } from '@domain/city-dto';
import { Observable, from } from 'rxjs';
import {environment} from "../app/environments/environment";

@Injectable()
export class ProjetoService {

  constructor(private http: HttpClient) {}

    //------------------------------------------------
    /** Recupera a lista de cidades */
    //------------------------------------------------
    pesquisarCidades(): Observable<CityDTO[]> {
    }

    //------------------------------------------------
    /** Exclui a cidade informada */
    //------------------------------------------------
    excluir(cidade: CityDTO): Observable<any> {
    }

    //------------------------------------------------
    /** Salva a cidade informada */
    //------------------------------------------------
    salvar(cidade: CityDTO): Observable<any> {
    }

}
