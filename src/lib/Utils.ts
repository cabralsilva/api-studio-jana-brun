export class Financial {
  getCurrencyFormat (value: number, precision = 2, prefix = 'R$ ', suffix = '', separatorDecimal = ',', separatorThousands = '.'): string {
    const sValue = Number.parseFloat(value.toString()).toFixed(precision)
    return prefix.concat(sValue).concat(suffix).replace(/\./g, separatorDecimal).replace(/\B(?=(\d{3})+(?!\d))/g, separatorThousands)
  }

  getTotalPrice (list: []): number {
    let totalPrice = 0
    list?.forEach((item: any) => {
      totalPrice = +item.totalPrice + totalPrice
    })
    return totalPrice
  }

  getPaidValue (list: []): number {
    let paidValue = 0
    list?.forEach((item: any) => {
      paidValue = +item.value + paidValue
    })
    return paidValue
  }

  getOpenValue (paidValue: number, total: number): number {
    if (paidValue > total) {
      return total
    }
    return total - paidValue
  }
}

export class Navigation {
  to (stack: any, to: string) {
    stack.push(to)
  }

  reloadPage () {
    window.location.reload()
  }
}

export class Validations {
  requiredObject (value: any) {
    if (value) {
      if (value.identifier) {
        return true
      }
      if (value.length) {
        return true
      }
    }
    return 'Obrigatório'
  }

  required (value: any) {
    return !!value || 'Obrigatório.'
  }
}

export function hashCode () {
  let hash = 0
  for (let i = 0; i < 5; i++) {
    hash = ((hash << 5) - hash) + (Math.random() * 100)
    hash = hash & hash // Convert to 32bit integer
  }
  return hash
}
