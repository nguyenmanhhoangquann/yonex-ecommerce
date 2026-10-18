import React, { useState, useEffect, useRef } from 'react';
import { Search, X, ArrowRight, Tag } from 'lucide-react';
import { useCart } from '../context/CartContext';
import { YONEX_PRODUCTS } from '../data/yonexData';

export default function SearchModal() {
  const { isSearchOpen, setIsSearchOpen, setQuickViewProduct } = useCart();
  const [searchTerm, setSearchTerm] = useState('');
  const inputRef = useRef(null);

  useEffect(() => {
    if (isSearchOpen) {
      setTimeout(() => inputRef.current?.focus(), 100);
    } else {
      setSearchTerm('');
    }
  }, [isSearchOpen]);

  if (!isSearchOpen) return null;

  const popularSearches = ["EZONE", "ASTROX", "VCORE", "Power Cushion", "PolyTour", "Bag", "Stan the Man"];

  const results = searchTerm.trim() === ''
    ? []
    : YONEX_PRODUCTS.filter(p => 
        p.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        p.category.toLowerCase().includes(searchTerm.toLowerCase()) ||
        p.description.toLowerCase().includes(searchTerm.toLowerCase())
      );

  const handleSelectProduct = (product) => {
    setIsSearchOpen(false);
    setQuickViewProduct(product);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-start justify-center pt-16 sm:pt-24 px-4 overflow-y-auto">
      {/* Backdrop */}
      <div 
        className="fixed inset-0 bg-black/70 backdrop-blur-sm transition-opacity"
        onClick={() => setIsSearchOpen(false)}
      />

      {/* Search Container */}
      <div className="relative bg-white w-full max-w-2xl rounded-sm shadow-2xl overflow-hidden z-10 animate-fade-in">
        {/* Search Input Bar */}
        <div className="p-4 sm:p-5 border-b border-gray-200 flex items-center gap-3">
          <Search size={22} className="text-gray-400 shrink-0" />
          <input
            ref={inputRef}
            type="text"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            placeholder="Search for racquets, footwear, strings, apparel..."
            className="w-full text-base sm:text-lg text-yonex-dark focus:outline-none placeholder:text-gray-400 font-medium"
          />
          {searchTerm && (
            <button
              onClick={() => setSearchTerm('')}
              className="p-1 text-gray-400 hover:text-black rounded"
            >
              <X size={18} />
            </button>
          )}
          <button
            onClick={() => setIsSearchOpen(false)}
            className="p-1.5 text-gray-400 hover:text-black rounded hover:bg-gray-100 transition-colors"
          >
            <X size={20} />
          </button>
        </div>

        {/* Content body */}
        <div className="p-4 sm:p-6 max-h-[70vh] overflow-y-auto">
          {searchTerm.trim() === '' ? (
            <div>
              <div className="flex items-center gap-2 text-xs font-bold uppercase tracking-wider text-gray-400 mb-3">
                <Tag size={13} />
                <span>Popular Searches</span>
              </div>
              <div className="flex flex-wrap gap-2">
                {popularSearches.map((term, i) => (
                  <button
                    key={i}
                    onClick={() => setSearchTerm(term)}
                    className="px-3 py-1.5 bg-gray-100 hover:bg-yonex-blue hover:text-white rounded-full text-xs font-semibold text-gray-700 transition-colors"
                  >
                    {term}
                  </button>
                ))}
              </div>

              {/* Suggestions */}
              <div className="mt-8">
                <h4 className="text-xs font-bold uppercase tracking-wider text-gray-400 mb-3">
                  Featured Products
                </h4>
                <div className="grid grid-cols-2 gap-3">
                  {YONEX_PRODUCTS.slice(0, 4).map(p => (
                    <div 
                      key={p.id}
                      onClick={() => handleSelectProduct(p)}
                      className="p-2 border border-gray-100 rounded hover:border-yonex-blue hover:bg-gray-50/50 cursor-pointer flex items-center gap-3 transition-all"
                    >
                      <img src={p.colors[0].image} alt={p.name} className="w-12 h-14 object-contain bg-gray-100 p-1 rounded shrink-0" />
                      <div>
                        <div className="text-xs font-bold text-yonex-dark">{p.name}</div>
                        <div className="text-xs text-yonex-blue font-semibold">${p.price.toFixed(2)}</div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          ) : results.length === 0 ? (
            <div className="py-12 text-center text-gray-500">
              <p className="text-sm">No products found matching "<strong>{searchTerm}</strong>".</p>
              <p className="text-xs text-gray-400 mt-1">Try searching for EZONE, ASTROX, or Footwear.</p>
            </div>
          ) : (
            <div>
              <div className="text-xs font-bold text-gray-400 uppercase tracking-wider mb-3">
                Found {results.length} results
              </div>
              <div className="divide-y divide-gray-100">
                {results.map((product) => (
                  <div
                    key={product.id}
                    onClick={() => handleSelectProduct(product)}
                    className="py-3 flex items-center justify-between hover:bg-gray-50 p-2 rounded cursor-pointer transition-colors"
                  >
                    <div className="flex items-center gap-3">
                      <div className="w-14 h-16 bg-gray-50 border border-gray-200 rounded p-1 shrink-0 flex items-center justify-center">
                        <img 
                          src={product.colors[0].image} 
                          alt={product.name}
                          className="max-h-full max-w-full object-contain"
                        />
                      </div>
                      <div>
                        <span className="text-[10px] font-semibold text-gray-400 uppercase tracking-wider block">
                          {product.category}
                        </span>
                        <h4 className="font-heading font-bold text-sm text-yonex-dark uppercase">
                          {product.name}
                        </h4>
                        <span className="text-xs text-gray-500">
                          {product.colors.length} color variants
                        </span>
                      </div>
                    </div>

                    <div className="text-right">
                      <div className="font-bold text-sm text-yonex-dark">
                        ${product.price.toFixed(2)}
                      </div>
                      <span className="text-xs font-bold text-yonex-blue hover:underline">
                        View Details &rarr;
                      </span>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
