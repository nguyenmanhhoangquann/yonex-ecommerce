import React, { useState } from 'react';
import { ArrowRight, Sparkles, Filter } from 'lucide-react';
import ProductCard from './ProductCard';
import { YONEX_PRODUCTS } from '../data/yonexData';

export default function FeaturedProducts() {
  const [activeCategory, setActiveCategory] = useState("All");

  const categories = ["All", "Tennis Racquets", "Badminton Racquets", "Footwear", "Bags", "Apparel", "Strings"];

  const filteredProducts = activeCategory === "All" 
    ? YONEX_PRODUCTS 
    : YONEX_PRODUCTS.filter(p => p.category === activeCategory);

  return (
    <section id="products" className="py-14 lg:py-20 max-w-[1920px] mx-auto px-4 sm:px-8">
      {/* Header & Category Filters */}
      <div className="flex flex-col md:flex-row md:items-end justify-between gap-4 mb-8 pb-4 border-b border-gray-200">
        <div>
          <div className="inline-flex items-center gap-1.5 text-xs font-bold text-yonex-blue uppercase tracking-widest mb-1">
            <Sparkles size={14} />
            <span>Tour-Tested Performance</span>
          </div>
          <h2 className="font-heading font-black text-3xl sm:text-4xl lg:text-5xl text-yonex-dark tracking-wide uppercase">
            NEW ARRIVALS
          </h2>
        </div>

        {/* Filter Pills */}
        <div className="flex items-center gap-1.5 overflow-x-auto pb-2 md:pb-0 scrollbar-none">
          {categories.map((cat) => (
            <button
              key={cat}
              onClick={() => setActiveCategory(cat)}
              className={`px-3.5 py-1.5 text-xs font-heading font-bold uppercase tracking-wider rounded whitespace-nowrap transition-all ${
                activeCategory === cat
                  ? 'bg-yonex-dark text-white shadow-sm'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              {cat}
            </button>
          ))}
        </div>
      </div>

      {/* Product Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {filteredProducts.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>

      {/* Bottom CTA */}
      <div className="mt-12 text-center">
        <a 
          href="#all-products"
          className="inline-flex items-center gap-2 border-2 border-yonex-dark text-yonex-dark px-8 py-3.5 font-heading font-bold text-xs uppercase tracking-widest rounded hover:bg-yonex-dark hover:text-white transition-all transform hover:-translate-y-0.5 shadow-sm"
        >
          <span>VIEW COMPLETE STORE CATALOG</span>
          <ArrowRight size={14} />
        </a>
      </div>
    </section>
  );
}
