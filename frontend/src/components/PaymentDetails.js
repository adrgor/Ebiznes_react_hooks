const PaymentDetails = (props) => {
    return (
        <div className="customer-info" style={{width: "40%", padding: "1em", minHeight: "70vh"}}>
            <form style={{display:"flex", flexDirection: "column"}}>
                <input placeholder="Card number" onChange={(e) => props.setCardNumber(e.target.value)}></input>
                <input placeholder="Expiration date" onChange={(e) => props.setExpDate(e.target.value)}></input>
                <input placeholder="Name on card" onChange={(e) => props.setNameOnCard(e.target.value)}></input>
                <input placeholder="CVV/VCV" onChange={(e) => props.setCvv(e.target.value)}></input>
            </form>
        </div>
    )
}

export default PaymentDetails