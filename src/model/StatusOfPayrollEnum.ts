export enum StatusOfPayrollEnum {
  PROCESSING = 'Em processamento',
  AWAITING_APPROVE = 'Aguardando aprovação',
  CANCELLED = 'Cancelado',
  CONFIRMED = 'Confirmado'
}

export const StatusOfPayrollList: {
    key: string;
    value: string;
}[] = Object.entries(StatusOfPayrollEnum).map(([key, value]) => ({ key, value }))

export type StatusOfPayrollType = keyof typeof StatusOfPayrollEnum

export const StatusOfPayrollEntries = Object.entries(StatusOfPayrollEnum)
