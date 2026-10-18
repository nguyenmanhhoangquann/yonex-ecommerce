import React, { useState, useEffect } from 'react';
import { X, ShoppingBag, Check, Star, Shield, ArrowRight } from 'lucide-react';
import { useCart } from '../context/CartContext';

export default function QuickViewModal() {
  const { quickViewProduct, setQuickViewProduct, addToCart } = useCart();
  const [selectedColorIndex, setSelectedColorIndex] = useState(0);
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    setSelectedColorIndex(0);
    setQuantity(1);
  }, [quickViewProduct]);

  if (!quickViewProduct) return null;

  const currentColor = quickViewProduct.colors[selectedColorIndex] || quickViewProduct.colors[0];

  const handleAddToCart = () => {
    addToCart(quickViewProduct, currentColor.name, quantity);
    setQuickViewProduct(null);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 sm:p-6 overflow-y-auto">
      {/* Backdrop */}
      <div 
        className="fixed inset-0 bg-black/70 backdrop-blur-sm transition-opacity"
        onClick={() => setQuickViewProduct(null)}
      />

      {/* Modal Dialog */}
      <div className="relative bg-white w-full max-w-4xl rounded-sm shadow-2xl overflow-hidden z-10 animate-fade-in my-8">
        <button
          onClick={() => setQuickViewProduct(null)}
          className="absolute top-4 right-4 z-20 p-2 text-gray-500 hover:text-black rounded-full hover:bg-gray-100 transition-colors"
          aria-label="Close dialog"
        >
          <X size={22} />
        </button>

        <div className="grid grid-cols-1 md:grid-cols-2">
          {/* Left: Product Image */}
          <div className="bg-[#F8F8F8] p-8 flex items-center justify-center relative min-h-[360px] md:min-h-[500px]">
            <img 
              src={currentColor.image} 
              alt={`${quickViewProduct.name} - ${currentColor.name}`}
              className="max-h-[420px] w-auto object-contain transition-all duration-300"
            />
            {quickViewProduct.badge && (
              <span className="absolute top-6 left-6 bg-yonex-blue text-white text-xs font-bold uppercase tracking-wider px-3 py-1 rounded">
                {quickViewProduct.badge}
              </span>
            )}
          </div>

          {/* Right: Product Details & Controls */}
          <div className="p-6 sm:p-8 flex flex-col justify-between overflow-y-auto max-h-[85vh]">
            <div>
              <span className="text-xs font-bold text-gray-500 uppercase tracking-widest block mb-1">
                {quickViewProduct.category}
              </span>

              <h2 className="font-heading font-black text-2xl sm:text-3xl text-yonex-dark tracking-wide uppercase">
                {quickViewProduct.name}
              </h2>

              {/* Rating */}
              <div className="flex items-center gap-2 mt-2">
                <div className="flex text-amber-400">
                  {[...Array(5)].map((_, i) => (
                    <Star key={i} size={14} fill="currentColor" />
                  ))}
                </div>
                <span className="text-xs font-bold text-gray-700">{quickViewProduct.rating || 4.9}</span>
                <span className="text-xs text-gray-400">({quickViewProduct.reviewsCount || 84} verified reviews)</span>
              </div>

              {/* Price */}
              <div className="mt-3 text-2xl font-heading font-black text-yonex-blue">
                ${quickViewProduct.price.toFixed(2)} USD
              </div>

              {/* Description */}
              <p className="mt-4 text-xs sm:text-sm text-gray-600 leading-relaxed font-normal">
                {quickViewProduct.description}
              </p>

              {/* Color Selector */}
              <div className="mt-6">
                <label className="text-xs font-bold uppercase text-yonex-dark tracking-wider block mb-2">
                  Select Color: <span className="text-gray-500 font-semibold">{currentColor.name}</span>
                </label>
                <div className="flex items-center gap-3">
                  {quickViewProduct.colors.map((color, idx) => (
                    <button
                      key={idx}
                      onClick={() => setSelectedColorIndex(idx)}
                      className={`flex items-center gap-2 px-3 py-1.5 rounded text-xs font-medium border transition-all ${
                        selectedColorIndex === idx
                          ? 'border-yonex-blue bg-blue-50/50 text-yonex-blue ring-1 ring-yonex-blue font-bold'
                          : 'border-gray-200 hover:border-gray-400 text-gray-700'
                      }`}
                    >
                      <span 
                        className="w-3.5 h-3.5 rounded-full border border-black/10 shrink-0" 
                        style={{ backgroundColor: color.code }}
                      />
                      <span>{color.name}</span>
                    </button>
                  ))}
                </div>
              </div>

              {/* Technical Specifications */}
              {quickViewProduct.specs && (
                <div className="mt-6 pt-4 border-t border-gray-100">
                  <h4 className="text-xs font-bold uppercase tracking-wider text-yonex-dark mb-2.5">
                    Technical Specifications
                  </h4>
                  <div className="grid grid-cols-2 gap-x-4 gap-y-1.5 text-xs">
                    {Object.entries(quickViewProduct.specs).map(([key, value]) => (
                      <div key={key} className="flex justify-between py-1 border-b border-gray-50">
                        <span className="text-gray-500">{key}:</span>
                        <span className="font-semibold text-yonex-dark text-right">{value}</span>
                      </div>
                    ))}
                  </div>
                </div>
              )}
            </div>

            {/* Actions: Quantity & Add to Cart */}
            <div className="mt-8 pt-4 border-t border-gray-200 space-y-3">
              <div className="flex items-center gap-3">
                <div className="flex items-center border border-gray-300 rounded h-11">
                  <button
                    onClick={() => setQuantity(Math.max(1, quantity - 1))}
                    className="px-3 text-gray-600 hover:bg-gray-100 h-full transition-colors"
                  >
                    -
                  </button>
                  <span className="px-4 text-xs font-bold text-yonex-dark">
                    {quantity}
                  </span>
                  <button
                    onClick={() => setQuantity(quantity + 1)}
                    className="px-3 text-gray-600 hover:bg-gray-100 h-full transition-colors"
                  >
                    +
                  </button>
                </div>

                <button
                  onClick={handleAddToCart}
                  className="flex-1 bg-yonex-blue text-white h-11 px-6 font-heading font-bold text-xs uppercase tracking-widest rounded hover:bg-yonex-darkBlue transition-all flex items-center justify-center gap-2 shadow-md"
                >
                  <ShoppingBag size={16} />
                  <span>ADD TO CART &bull; ${(quickViewProduct.price * quantity).toFixed(2)}</span>
                </button>
              </div>

              <div className="flex items-center justify-center gap-4 text-[11px] text-gray-500">
                <span className="flex items-center gap-1">
                  <Shield size={13} className="text-yonex-green" /> 100% Genuine Yonex
                </span>
                <span>•</span>
                <span>Free Returns Within 30 Days</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
