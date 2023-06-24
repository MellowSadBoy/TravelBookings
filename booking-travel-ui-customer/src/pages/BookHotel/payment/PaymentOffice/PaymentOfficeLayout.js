import React from 'react'
import PaymentOffice from './PaymentOffice'
import PaymentLayout from '~/layout/PaymentLayout/PaymentLayout'

function PaymentOfficeLayout() {
  return (
    <div>
        <PaymentLayout child={<PaymentOffice/>}/>
    </div>
  )
}

export default PaymentOfficeLayout