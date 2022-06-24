export enum TypeOfCurrencyEnum {
    CASH = 'Dinheiro',
    CHECK = 'Cheque',
    DEBIT_CARD = 'Cartão de débito',
    CREDIT_CARD = 'Cartão de crédito',
    CREDIT = 'Crédito',
    TRADE_NOTE = 'Boleto',
    BONUS = 'Bonus',
    PIX = 'Pix'
}

export const TypeOfCurrencyList: {
    key: string;
    value: string;
}[] = Object.entries(TypeOfCurrencyEnum).map(([key, value]) => ({ key, value }))

export type TypeOfCurrencyType = keyof typeof TypeOfCurrencyEnum

export const TypeOfCurrencyEntries = Object.entries(TypeOfCurrencyEnum)
