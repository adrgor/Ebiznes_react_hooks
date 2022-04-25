import CustomerInfo from './CustomerInfo'
import CheckoutSummary from './CheckoutSummary'
import PaymentDetails from './PaymentDetails'
import { useState } from 'react'

export const Checkout = () => {
    const [isCardView, setIsCardView] = useState(true)
    const changeView = () => {
        setIsCardView((prevValue) => !prevValue)
    }

    const [email, setEmail] = useState("")
    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    const [street, setStreet] = useState("")
    const [buildingNumber, setBuildingNumber] = useState("")
    const [apartmentNumber, setApartmentNumber] = useState("")
    const [city, setCity] = useState("")
    const [postalCode, setPostalCode] = useState("")
    const [country, setCountry] = useState("")
    const [phone, setPhone] = useState("")
    const [cardNumber, setCardNumber] = useState("")
    const [expDate, setExpDate] = useState("")
    const [nameOnCard, setNameOnCard] = useState("")
    const [cvv, setCvv] = useState("")
    

    return (
        <div className="checkout-page">
            {isCardView && <CustomerInfo setEmail={setEmail} email={email} setFirstName={setFirstName} firstName={firstName} setLastName={setLastName} lastName={lastName} setStreet={setStreet} street={street} 
                            setBuildingNumber={setBuildingNumber} buildingNumber={buildingNumber} setApartmentNumber={setApartmentNumber} apartmentNumber={apartmentNumber} setCity={setCity} city={city} setPostalCode={setPostalCode} 
                            postalCode={postalCode} setCountry={setCountry} country={country} setPhone={setPhone} phone={phone}/>}

            {!isCardView && <PaymentDetails setCardNumber={setCardNumber} cardNumber={cardNumber} setExpDate={setExpDate} expDate={expDate} setNameOnCard={setNameOnCard} nameOnCard={nameOnCard} setCvv={setCvv} cvv={cvv}/>}

            <CheckoutSummary changeView={changeView} isCardView={isCardView} email={email} firstName={firstName} lastName={lastName} street={street} buildingNumber={buildingNumber} apartmentNumber={apartmentNumber} postalCode={postalCode} country={country} city={city} phone={phone} cardNumber={cardNumber} 
                             expDate={expDate} nameOnCard={nameOnCard} cvv={cvv}/>
        </div>
    )
}
