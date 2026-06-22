import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CityDTO } from '@domain/city-dto';
import { Observable } from 'rxjs';
import { environment } from '../app/environments/environment';

@Injectable()
export class ProjetoService {

  constructor(private http: HttpClient) {}

    //------------------------------------------------
    /** Recupera a lista de cidades */
    //------------------------------------------------
    pesquisarCidades(): Observable<CityDTO[]> {
        return this.http.get<CityDTO[]>(`${environment.apiUrl}${environment.urlCidades}`);
    }

    //------------------------------------------------
    /** Exclui a cidade informada */
    //------------------------------------------------
    excluir(cidade: CityDTO): Observable<void> {
        return this.http.delete<void>(`${environment.apiUrl}${environment.urlCidades}/${cidade.id}`);
    }

    //------------------------------------------------
    /** Salva a cidade informada */
    //------------------------------------------------
    salvar(cidade: CityDTO): Observable<void> {
        const url = `${environment.apiUrl}${environment.urlCidades}`;

        if (cidade.id) {
            return this.http.put<void>(url, cidade);
        }

        return this.http.post<void>(url, {
            nome: cidade.nome,
            uf: cidade.uf,
            capital: cidade.capital
        });
    }

}
