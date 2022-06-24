export enum MatriculationStatusEnum {
    PRE_REGISTER = 'Pré-matrícula',
    EFFECTIVE = 'Confirmada',
    CANCELLED = 'Cancelada'
}

export const MatriculationStatusList: {
    key: string;
    value: string;
}[] = Object.entries(MatriculationStatusEnum).map(([key, value]) => ({ key, value }))

export type MatriculationStatusType = keyof typeof MatriculationStatusEnum

export const MatriculationStatusEntries = Object.entries(MatriculationStatusEnum)
