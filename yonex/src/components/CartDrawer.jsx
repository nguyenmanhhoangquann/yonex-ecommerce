import React from 'react';
import { X, Trash2, Plus, Minus, ShoppingBag, ArrowRight, ShieldCheck, Truck } from 'lucide-react';
import { useCart } from '../context/CartContext';

export default function CartDrawer() {
  const {
    cartItems,
    isCartOpen,
    setIsCartOpen,
    removeFromCart,
    updateQuantity,
    subtotal,
    totalCount,
    amountToFreeShipping,
    progressToFreeShipping
  } = useCart();

  if (!isCartOpen) return null;

  return (
    <div className="fixed inset-0 z-50 overflow-hidden">
      {/* Backdrop */}
      <div 
        className="fixed inset-0 bg-black/60 backdrop-blur-sm transition-opacity"
        onClick={() => setIsCartOpen(false)}
      />

      <div className="fixed inset-y-0 right-0 max-w-full flex pl-10">
        <div className="w-screen max-w-md bg-white shadow-2xl flex flex-col justify-between animate-fade-in">
          {/* Header */}
          <div className="p-4 sm:p-6 border-b border-gray-100 flex items-center justify-between">
            <div className="flex items-center gap-2">
              <ShoppingBag size={20} className="text-yonex-blue" />
              <h3 className="font-heading font-black text-xl text-yonex-dark tracking-wide uppercase">
                YOUR CART ({totalCount})
              </h3>
            </div>

            <button 
              onClick={() => setIsCartOpen(false)}
              className="p-1.5 text-gray-500 hover:text-yonex-dark rounded-full hover:bg-gray-100 transition-colors"
            >
              <X size={20} />
            </button>
          </div>

          {/* Free Shipping Meter */}
          <div className="bg-gray-50 px-4 sm:px-6 py-3 border-b border-gray-200">
            <div className="flex items-center gap-2 text-xs font-bold text-yonex-dark mb-1.5">
              <Truck size={15} className="text-yonex-blue shrink-0" />
              {amountToFreeShipping > 0 ? (
                <span>
                  Add <strong className="text-yonex-blue">${amountToFreeShipping.toFixed(2)}</strong> more to unlock <span className="text-yonex-green">FREE SHIPPING</span>
                </span>
              ) : (
                <span className="text-yonex-green font-bold">
                  🎉 Congratulations! You qualify for Free US Shipping.
                </span>
              )}
            </div>

            <div className="w-full bg-gray-200 h-2 rounded-full overflow-hidden">
              <div 
                className="bg-gradient-to-r from-yonex-blue to-yonex-green h-full transition-all duration-500 rounded-full"
                style={{ width: `${progressToFreeShipping}%` }}
              />
            </div>
          </div>

          {/* Items List */}
          <div className="flex-1 overflow-y-auto p-4 sm:p-6 divide-y divide-gray-100">
            {cartItems.length === 0 ? (
              <div className="py-16 text-center">
                <div className="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4 text-gray-400">
                  <ShoppingBag size={28} />
                </div>
                <h4 className="font-heading font-bold text-lg text-yonex-dark uppercase">
                  Your cart is empty
                </h4>
                <p className="text-xs text-gray-500 mt-1 max-w-xs mx-auto">
                  Looks like you haven't added any racquets, apparel or gear to your bag yet.
                </p>
                <button
                  onClick={() => setIsCartOpen(false)}
                  className="mt-6 bg-yonex-blue text-white px-6 py-3 font-heading font-bold text-xs uppercase tracking-wider rounded hover:bg-yonex-darkBlue transition-colors"
                >
                  START SHOPPING
                </button>
              </div>
            ) : (
              cartItems.map((item) => (
                <div key={`${item.id}-${item.color}`} className="py-4 flex gap-4">
                  {/* Thumbnail */}
                  <div className="w-20 h-24 bg-gray-50 border border-gray-200 rounded p-1 shrink-0 flex items-center justify-center">
                    <img 
                      src={item.image} 
                      alt={item.name} 
                      className="max-h-full max-w-full object-contain"
                    />
                  </div>

                  {/* Info */}
                  <div className="flex-1 flex flex-col justify-between">
                    <div>
                      <div className="flex items-start justify-between">
                        <h4 className="font-heading font-bold text-sm text-yonex-dark tracking-wide uppercase">
                          {item.name}
                        </h4>
                        <button
                          onClick={() => removeFromCart(item.id, item.color)}
                          className="text-gray-400 hover:text-red-600 transition-colors p-1"
                          title="Remove item"
                        >
                          <Trash2 size={15} />
                        </button>
                      </div>

                      <p className="text-xs text-gray-500 mt-0.5 font-medium">
                        Color: {item.color}
                      </p>

                      <div className="text-xs font-bold text-yonex-dark mt-1">
                        ${item.price.toFixed(2)} USD
                      </div>
                    </div>

                    {/* Quantity controls */}
                    <div className="flex items-center justify-between mt-2">
                      <div className="flex items-center border border-gray-300 rounded overflow-hidden">
                        <button 
                          onClick={() => updateQuantity(item.id, item.color, -1)}
                          className="p-1 px-2 hover:bg-gray-100 text-gray-600 transition-colors"
                          aria-label="Decrease quantity"
                        >
                          <Minus size={12} />
                        </button>
                        <span className="px-2.5 text-xs font-bold text-yonex-dark">
                          {item.quantity}
                        </span>
                        <button 
                          onClick={() => updateQuantity(item.id, item.color, 1)}
                          className="p-1 px-2 hover:bg-gray-100 text-gray-600 transition-colors"
                          aria-label="Increase quantity"
                        >
                          <Plus size={12} />
                        </button>
                      </div>

                      <div className="text-xs font-bold text-yonex-blue">
                        ${(item.price * item.quantity).toFixed(2)}
                      </div>
                    </div>
                  </div>
                </div>
              ))
            )}
          </div>

          {/* Footer & Checkout */}
          {cartItems.length > 0 && (
            <div className="p-4 sm:p-6 bg-gray-50 border-t border-gray-200 space-y-4">
              <div className="space-y-1.5 text-sm">
                <div className="flex justify-between text-gray-600">
                  <span>Subtotal</span>
                  <span className="font-bold text-yonex-dark">${subtotal.toFixed(2)} USD</span>
                </div>
                <div className="flex justify-between text-gray-600">
                  <span>Estimated Shipping</span>
                  <span className="font-medium">
                    {subtotal >= 99 ? (
                      <span className="text-yonex-green font-bold">FREE</span>
                    ) : (
                      "$9.99 USD"
                    )}
                  </span>
                </div>
                <p className="text-[11px] text-gray-400">
                  Taxes, discounts, and customs calculated during checkout.
                </p>
              </div>

              <div className="space-y-2">
                <button 
                  onClick={() => alert(`Proceeding to checkout with total: $${(subtotal >= 99 ? subtotal : subtotal + 9.99).toFixed(2)} USD`)}
                  className="w-full bg-yonex-blue text-white py-4 font-heading font-bold text-sm tracking-widest uppercase rounded hover:bg-yonex-darkBlue transition-all flex items-center justify-center gap-2 shadow-md transform hover:-translate-y-0.5"
                >
                  <span>PROCEED TO CHECKOUT</span>
                  <ArrowRight size={16} />
                </button>

                <button 
                  onClick={() => setIsCartOpen(false)}
                  className="w-full bg-transparent text-gray-600 py-2 text-xs font-bold uppercase tracking-wider hover:text-yonex-dark transition-colors"
                >
                  Continue Shopping
                </button>
              </div>

              <div className="flex items-center justify-center gap-1.5 text-[11px] text-gray-500 pt-1">
                <ShieldCheck size={14} className="text-yonex-green" />
                <span>Secure SSL 256-bit Encrypted Checkout</span>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
