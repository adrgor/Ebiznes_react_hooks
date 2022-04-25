import React, { useContext } from 'react'
import {useNavigate} from 'react-router-dom'
import { BasketContext, SetBasketContext } from '../App'


const CheckoutSummary = (props) => {
    const buttonStyle = {
        width: "fit-content",
        textAlign: "center",
        marginTop: "2em",
        fontSize: "2em",
        padding: "0.5em",
        paddingLeft: "1em",
        paddingRight: "1em",
        backgroundColor: "#000e29",
        borderRadius: "50000px",
        color: "#efefef",
        minWidth: "5em"
    }

    const summaryStye = {
        margin: "auto",
        marginTop: "2.5em",
        fontSize: "2em",
        paddingLeft: "1em",
        color: "#000e29",
    }

    const navigate = useNavigate()
    const basket = useContext(BasketContext)
    const setBasket = useContext(SetBasketContext)


    const sendPaymentDetails = () => {
        fetch('http://localhost:8080/api/details', {
            mode: 'cors',
            method: "POST",
            headers: {
                "Content-Type": 'application/json'
            }, 
            body: JSON.stringify({
                email: props.email,
                firstName: props.firstName,
                lastName: props.lastName,
                street: props.street,
                buildingNumber: props.buildingNumber,
                apartmentNumber: props.apartmentNumber,
                city: props.city,
                postalCode: props.postalCode,
                country: props.country,
                phoneNumber: props.phone,
                cardNumber: props.cardNumber,
                expDate: props.expDate,
                nameOnCard: props.nameOnCard,
                cvv: props.cvv
            })
        })
        console.log('wysłano dane')
        setBasket([])
        navigate('/')
    }

    return (
        <div >
            <div style={summaryStye}>Total price: ${basket.map(item => item.price).reduce((partialSum, a) => partialSum + a, 0)}</div>
            {
                !props.isCardView ?
                <>
                <div style={buttonStyle} onClick={() => {sendPaymentDetails()}}>Buy ></div>
                <div style={buttonStyle} onClick={props.changeView}>Go back</div>
                </> : 
                <div style={buttonStyle} onClick={props.changeView}>Go to payment details ></div>
            }
            
        </div>
    )
}

export default CheckoutSummary