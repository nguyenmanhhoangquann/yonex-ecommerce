import React, { useState } from 'react';
import { Eye, ShoppingBag, Check } from 'lucide-react';
import { useCart } from '../context/CartContext';

export default function ProductCard({ product }) {
  const [selectedColorIndex, setSelectedColorIndex] = useState(0);
  const [isHovered, setIsHovered] = useState(false);
  const { addToCart, setQuickViewProduct } = useCart();

  const currentColor = product.colors[selectedColorIndex] || product.colors[0];

  const handleQuickAdd = (e) => {
    e.stopPropagation();
    addToCart(product, currentColor.name);
  };

  return (
    <div 
      className="group flex flex-col bg-white border border-gray-100 rounded-sm overflow-hidden hover:border-gray-300 hover:shadow-xl transition-all duration-300"
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      {/* Product Image Frame */}
      <div 
        className="relative aspect-[4/5] bg-[#F8F8F8] overflow-hidden cursor-pointer"
        onClick={() => setQuickViewProduct(product)}
      >
        <img 
          src={currentColor.image} 
          alt={`${product.name} - ${currentColor.name}`}
          className="w-full h-full object-contain object-center p-4 group-hover:scale-105 transition-transform duration-500"
          loading="lazy"
        />

        {/* Top Badges */}
        {product.badge && (
          <div className="absolute top-3 left-3 z-10">
            <span className="bg-yonex-blue text-white text-[10px] font-bold uppercase tracking-wider px-2 py-0.5 rounded shadow-sm">
              {product.badge}
            </span>
          </div>
        )}

        {/* Hover Quick Action Buttons */}
        <div className={`absolute bottom-3 inset-x-3 z-10 flex gap-2 transition-all duration-300 ${
          isHovered ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-3 pointer-events-none'
        }`}>
          <button
            onClick={handleQuickAdd}
            className="flex-1 bg-yonex-dark text-white text-xs font-heading font-bold uppercase tracking-wider py-2.5 px-3 rounded hover:bg-yonex-blue transition-colors flex items-center justify-center gap-1.5 shadow-md"
            title="Add to Cart"
          >
            <ShoppingBag size={14} />
            <span>Add to Cart</span>
          </button>

          <button
            onClick={(e) => {
              e.stopPropagation();
              setQuickViewProduct(product);
            }}
            className="bg-white text-yonex-dark text-xs p-2.5 rounded border border-gray-200 hover:bg-gray-100 transition-colors shadow-md"
            title="Quick View Details"
          >
            <Eye size={15} />
          </button>
        </div>
      </div>

      {/* Product Details */}
      <div className="p-4 flex-1 flex flex-col justify-between">
        <div>
          <div className="text-[11px] font-semibold text-gray-500 uppercase tracking-wider mb-1">
            {product.category}
          </div>

          <h3 
            className="font-heading font-bold text-base text-yonex-dark tracking-wide uppercase hover:text-yonex-blue transition-colors cursor-pointer"
            onClick={() => setQuickViewProduct(product)}
          >
            {product.name}
          </h3>

          <div className="mt-1 flex items-baseline gap-2">
            <span className="text-sm font-bold text-yonex-dark">
              ${product.price.toFixed(2)} USD
            </span>
          </div>
        </div>

        {/* Color Swatches */}
        <div className="mt-3 pt-3 border-t border-gray-100 flex items-center justify-between">
          <div className="flex items-center gap-1.5">
            {product.colors.map((color, idx) => (
              <button
                key={idx}
                onClick={() => setSelectedColorIndex(idx)}
                className={`w-4 h-4 rounded-full border transition-all ${
                  selectedColorIndex === idx 
                    ? 'ring-2 ring-yonex-blue ring-offset-1 scale-110' 
                    : 'border-gray-300 hover:scale-105'
                }`}
                style={{ backgroundColor: color.code }}
                title={color.name}
                aria-label={`Select ${color.name}`}
              />
            ))}
          </div>

          <span className="text-[11px] text-gray-500 font-medium">
            {product.colors.length} {product.colors.length > 1 ? 'colors' : 'color'}
          </span>
        </div>
      </div>
    </div>
  );
}
