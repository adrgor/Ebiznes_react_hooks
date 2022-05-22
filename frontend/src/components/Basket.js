import {React, useContext} from 'react'
import BasketItem from './BasketItem'
import { Link } from 'react-router-dom';
import { BasketContext, SetBasketContext } from '../App';

const Basket = () => {
    const basket = useContext(BasketContext)
    const setBasket = useContext(SetBasketContext)

    const sendBasketState = () => {
        fetch('http://localhost:8080/api/basket', {
            mode: 'cors',
            method: "POST",
            headers: {
                "Content-Type": 'application/json',
                "Authorization": `Bearer ${localStorage.getItem("JWT_TOKEN")}`
                
            }, 
            body: JSON.stringify(basket)
        })
    }

    const removeItemFromBasket = (id) => {
        let newBasket = [...basket]
        newBasket.splice(id, 1)
        setBasket(newBasket)
    }


    return (
        <div className="basket">
            <p>Your basket</p>

            {basket.length > 0 ? basket.map((item, index) => (<BasketItem id={index} name={item.name} price={item.price} removeItemFromBasket={(id) => removeItemFromBasket(id)}/>)) :
            <div style={{margin: "auto", textAlign: "center"}}>No products in basket</div>}
            <div className="summary">
                Summary: ${basket.map(item => item.price).reduce((partialSum, a) => partialSum + a, 0)}
            </div>
            {basket.length > 0 && <Link to="/checkout"> <div className="checkout" onClick={sendBasketState}>Go to checkout ></div></Link>}
        </div>
    );
}


export default Basket;
