export enum StatusOfBillEnum {
  OPENED = 'Aberto',
  PARTIALLY = 'Parcialmento pago',
  CANCELLED = 'Cancelado',
  SETTLED = 'Pago/Quitado',
  RENEGOTIATED = 'Renegociado',
  REFUND = 'Devolvido'
}

export const StatusOfBillList: {
    key: string;
    value: string;
}[] = Object.entries(StatusOfBillEnum).map(([key, value]) => ({ key, value }))

export type StatusOfBillType = keyof typeof StatusOfBillEnum

export const StatusOfBillEntries = Object.entries(StatusOfBillEnum)
