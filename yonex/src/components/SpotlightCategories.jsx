import React from 'react';
import { ArrowRight } from 'lucide-react';
import { SPOTLIGHT_CATEGORIES } from '../data/yonexData';

export default function SpotlightCategories() {
  return (
    <section className="py-14 lg:py-20 max-w-[1920px] mx-auto px-4 sm:px-8">
      {/* Header */}
      <div className="flex items-center justify-between mb-8 pb-3 border-b border-gray-200">
        <div>
          <span className="text-xs font-bold text-yonex-blue uppercase tracking-widest block mb-1">
            Curated Collections
          </span>
          <h2 className="font-heading font-black text-2xl sm:text-3xl lg:text-4xl text-yonex-dark tracking-wide uppercase">
            SPOTLIGHT CATEGORIES
          </h2>
        </div>
      </div>

      {/* 3-Column Grid */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 lg:gap-8">
        {SPOTLIGHT_CATEGORIES.map((cat) => (
          <div 
            key={cat.id}
            className="group relative overflow-hidden bg-gray-100 rounded-sm flex flex-col justify-end aspect-[4/5] shadow-sm hover:shadow-xl transition-all duration-500"
          >
            {/* Image */}
            <img 
              src={cat.image} 
              alt={cat.title}
              className="absolute inset-0 w-full h-full object-cover object-center group-hover:scale-105 transition-transform duration-700 ease-out"
              loading="lazy"
            />

            {/* Dark gradient overlay */}
            <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/30 to-transparent group-hover:from-black/85 transition-all duration-300" />

            {/* Content info */}
            <div className="relative z-10 p-6 sm:p-8 text-white">
              <span className="inline-block text-[11px] font-bold uppercase tracking-widest text-yonex-green bg-black/40 backdrop-blur-sm px-2.5 py-1 rounded mb-2">
                {cat.tag}
              </span>

              <h3 className="font-heading font-bold text-xl sm:text-2xl text-white tracking-wide uppercase">
                {cat.title}
              </h3>

              <p className="text-xs sm:text-sm text-gray-200 mt-1.5 font-medium">
                {cat.subtitle}
              </p>

              <div className="mt-4 pt-4 border-t border-white/20 flex items-center justify-between">
                <span className="text-xs font-heading font-bold tracking-widest uppercase text-white group-hover:text-yonex-blue transition-colors flex items-center gap-1.5">
                  {cat.linkText} <ArrowRight size={14} className="group-hover:translate-x-1 transition-transform" />
                </span>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}
