export enum StatusActiveEnum {
    ACTIVE = 'Ativo',
    INACTIVE = 'Inativo'
}

export const StatusActiveList: {
    key: string;
    value: string;
}[] = Object.entries(StatusActiveEnum).map(([key, value]) => ({ key, value }))

export type StatusActiveType = keyof typeof StatusActiveEnum

export const StatusActiveEntries = Object.entries(StatusActiveEnum)
