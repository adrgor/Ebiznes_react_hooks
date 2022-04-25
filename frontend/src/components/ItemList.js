import React, { useEffect, useState, useContext } from 'react'
import Item from './Item'
import { Link } from 'react-router-dom'
import { BasketContext } from '../App'

const ItemList = () => {
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
        minWidth: "5em",
        margin: "auto"
    }

    const [items, setItems] = useState([]) 
    const basket = useContext(BasketContext)

    useEffect(() => {
        const getItems = async () => {
          const itemsFromServer = await fetchItems()
          setItems(itemsFromServer)
        }
    
        getItems()
    
      }, [])
    
      const fetchItems = async () => {
        const res = await fetch('http://localhost:8080/api/products', {
          mode: 'cors'
        })
        const data = await res.json()
    
        return data
      }

    return (
        <>
        <div className="item-list">
            { items.length <= 0 ? <div>No products found</div> : items.map(item => (<Item id={item.id} name={item.name} price={item.price}/>)) }
        </div>
        {basket.length > 0 && <Link to="/basket"><div style={buttonStyle}>Go to basket ({basket.length})></div></Link>}
        </>
    );
}


export default ItemList;
