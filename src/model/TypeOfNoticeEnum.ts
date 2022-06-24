export enum TypeOfNoticeEnum {
    INFORMATION = 'Informação',
    WARNING = 'Alerta',
    IMPORTANT = 'Importante'
}

export const TypeOfNoticeList: {
    key: string;
    value: string;
}[] = Object.entries(TypeOfNoticeEnum).map(([key, value]) => ({ key, value }))

export type TypeOfNoticeType = keyof typeof TypeOfNoticeEnum

export const TypeOfNoticeEntries = Object.entries(TypeOfNoticeEnum)
