const CustomerInfo = (props) => {
    return (
        <div className="customer-info" style={{width: "40%", padding: "1em"}}>
            <form style={{display:"flex", flexDirection: "column"}}>
                <input placeholder="Email address * " onChange={(e) => props.setEmail(e.target.value)}></input>
                <input placeholder="Confirm email address * "></input>
                <input placeholder="First name * " onChange={(e) => props.setFirstName(e.target.value)}></input>
                <input placeholder="Last name * " onChange={(e) => props.setLastName(e.target.value)}></input>
                <input placeholder="Street * " onChange={(e) => props.setStreet(e.target.value)}></input>
                <input placeholder="Building number * " onChange={(e) => props.setBuildingNumber(e.target.value)}></input>
                <input placeholder="Apartment number" onChange={(e) => props.setApartmentNumber(e.target.value)}></input>
                <input placeholder="City * " onChange={(e) => props.setCity(e.target.value)}></input>
                <input placeholder="Postal code * " onChange={(e) => props.setPostalCode(e.target.value)}></input>
                <input placeholder="Country * " onChange={(e) => props.setCountry(e.target.value)}></input>
                <input placeholder="Phone number * " onChange={(e) => props.setPhone(e.target.value)}></input>
            </form>
        </div>
    )
}

export default CustomerInfo