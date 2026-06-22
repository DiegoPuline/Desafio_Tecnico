
//---------------------------------------------------
/** Tipos de comércio disponíveis no sistema */
//---------------------------------------------------
export enum CommerceType {
    FARMACIA = 'FARMACIA',
    PADARIA = 'PADARIA',
    POSTO_GASOLINA = 'POSTO_GASOLINA',
    LANCHONETE = 'LANCHONETE'
}

//---------------------------------------------------
/** DTO que guarda os dados de um comércio */
//---------------------------------------------------
export class CommerceDTO {
    id?: number;
    nome?: string;
    nomeResponsavel?: string;
    tipo?: CommerceType;
    cidadeId?: number;
}
