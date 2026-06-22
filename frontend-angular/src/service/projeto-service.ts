import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CityDTO } from '@domain/city-dto';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
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
        return this.http.delete(
            `${environment.apiUrl}${environment.urlCidades}/${cidade.id}`,
            { responseType: 'text' }
        ).pipe(map(() => void 0));
    }

    //------------------------------------------------
    /** Salva a cidade informada */
    //------------------------------------------------
    salvar(cidade: CityDTO): Observable<void> {
        const url = `${environment.apiUrl}${environment.urlCidades}`;
        const payload = {
            nome: cidade.nome?.trim(),
            uf: cidade.uf?.trim().toUpperCase(),
            capital: cidade.capital ?? false
        };

        if (cidade.id) {
            return this.http.put(url, { id: cidade.id, ...payload }, { responseType: 'text' })
                .pipe(map(() => void 0));
        }

        return this.http.post(url, payload, { responseType: 'text' })
            .pipe(map(() => void 0));
    }

}
