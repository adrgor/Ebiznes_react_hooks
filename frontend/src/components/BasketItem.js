import React from 'react'
import placeholderImg from '../images/placeholder.png'

const BasketItem = (props) => {

    return (
        <div className="basket-item">
            <img src={placeholderImg} alt="Placeholder" style={{height: "100%"}}/>
            <div style={{margin: "auto"}}>{props.name}</div>
            <div style={{border: "1px solid black", height: "50px", paddingLeft: "2em", paddingRight: "2em",  margin: "auto", textAlign: "center", lineHeight: "50px"}}
                onClick={() => props.removeItemFromBasket(props.id)}>Remove</div>
            <div style={{margin:"auto"}}>{props.price}</div>
        </div>
    );
}


export default BasketItem;
