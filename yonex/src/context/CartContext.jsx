import React, { createContext, useContext, useState, useEffect } from 'react';

const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState(() => {
    try {
      const saved = localStorage.getItem('yonex_cart');
      return saved ? JSON.parse(saved) : [
        {
          id: "ezone-100",
          name: "EZONE 100",
          price: 305.00,
          color: "Espresso Brown",
          image: "https://us.yonex.com/cdn/shop/files/ALL_08EZ100_839-1.jpg?v=1785871338&width=800",
          quantity: 1
        }
      ];
    } catch {
      return [];
    }
  });

  const [isCartOpen, setIsCartOpen] = useState(false);
  const [quickViewProduct, setQuickViewProduct] = useState(null);
  const [isSearchOpen, setIsSearchOpen] = useState(false);
  const [toastMessage, setToastMessage] = useState(null);

  useEffect(() => {
    try {
      localStorage.setItem('yonex_cart', JSON.stringify(cartItems));
    } catch (e) {
      console.error(e);
    }
  }, [cartItems]);

  const showToast = (msg) => {
    setToastMessage(msg);
    setTimeout(() => {
      setToastMessage(null);
    }, 3000);
  };

  const addToCart = (product, colorName, quantity = 1) => {
    const selectedColor = product.colors.find(c => c.name === colorName) || product.colors[0];
    setCartItems(prev => {
      const itemKey = `${product.id}-${selectedColor.name}`;
      const existing = prev.find(item => `${item.id}-${item.color}` === itemKey);
      if (existing) {
        return prev.map(item => 
          `${item.id}-${item.color}` === itemKey
            ? { ...item, quantity: item.quantity + quantity }
            : item
        );
      } else {
        return [
          ...prev,
          {
            id: product.id,
            name: product.name,
            price: product.price,
            color: selectedColor.name,
            image: selectedColor.image,
            quantity: quantity
          }
        ];
      }
    });

    showToast(`Added ${product.name} (${selectedColor.name}) to cart`);
    setIsCartOpen(true);
  };

  const removeFromCart = (id, color) => {
    setCartItems(prev => prev.filter(item => !(item.id === id && item.color === color)));
  };

  const updateQuantity = (id, color, delta) => {
    setCartItems(prev => 
      prev.map(item => {
        if (item.id === id && item.color === color) {
          const newQty = item.quantity + delta;
          return newQty > 0 ? { ...item, quantity: newQty } : null;
        }
        return item;
      }).filter(Boolean)
    );
  };

  const subtotal = cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  const totalCount = cartItems.reduce((sum, item) => sum + item.quantity, 0);
  const freeShippingThreshold = 99;
  const progressToFreeShipping = Math.min(100, (subtotal / freeShippingThreshold) * 100);
  const amountToFreeShipping = Math.max(0, freeShippingThreshold - subtotal);

  return (
    <CartContext.Provider value={{
      cartItems,
      addToCart,
      removeFromCart,
      updateQuantity,
      subtotal,
      totalCount,
      isCartOpen,
      setIsCartOpen,
      quickViewProduct,
      setQuickViewProduct,
      isSearchOpen,
      setIsSearchOpen,
      freeShippingThreshold,
      progressToFreeShipping,
      amountToFreeShipping,
      toastMessage
    }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => useContext(CartContext);
