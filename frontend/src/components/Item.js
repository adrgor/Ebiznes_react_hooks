import React, { useContext } from 'react'
import { SetBasketContext } from '../App'
import placeholderImg from '../images/placeholder.png'

const Item = (props) => {
    
const setBasket = useContext(SetBasketContext)

    const addToBasketClicked = () => {
        setBasket(prev => ([...prev, {id: props.id, name: props.name, price: props.price}]))
    }

    return (
        <div className="item">
            <img src={placeholderImg} alt="Placeholder" style={{width: "100%"}}/>
            <div className="details">
                <div style={{height:"2em"}}>{props.name}</div>
                <div style={{padding: "0.5em", maxHeight: "1em"}}>{props.price + "zł"}</div>
                <div style={{borderTop: "1px solid black", margin: "auto", textAlign: "center"}} onClick={addToBasketClicked}>Add to basket</div>
            </div>
        </div>
    )
}

export default Item
