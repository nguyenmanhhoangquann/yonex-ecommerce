import React from 'react';
import { ArrowRight } from 'lucide-react';
import { INTELLIGENT_DESIGN } from '../data/yonexData';

export default function IntelligentlyDesigned() {
  return (
    <section className="py-14 lg:py-20 max-w-[1920px] mx-auto px-4 sm:px-8">
      {/* Title */}
      <div className="text-center max-w-3xl mx-auto mb-12">
        <span className="text-xs font-bold text-yonex-blue uppercase tracking-widest block mb-1">
          Precision Engineering
        </span>
        <h2 className="font-heading font-black text-3xl sm:text-4xl lg:text-5xl text-yonex-dark tracking-wide uppercase">
          INTELLIGENTLY DESIGNED
        </h2>
        <p className="mt-3 text-sm sm:text-base text-gray-600 font-medium">
          Driven by a passion for peak performance, from precision-engineered accessories to the essentials that elevate every game.
        </p>
      </div>

      {/* Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {INTELLIGENT_DESIGN.map((item) => (
          <div 
            key={item.id}
            className="group relative bg-white border border-gray-200 rounded-sm overflow-hidden flex flex-col justify-between hover:border-yonex-blue hover:shadow-lg transition-all duration-300"
          >
            {/* Image */}
            <div className="aspect-[4/3] overflow-hidden bg-gray-100 relative">
              <img 
                src={item.image} 
                alt={item.title}
                className="w-full h-full object-cover object-center group-hover:scale-105 transition-transform duration-500"
                loading="lazy"
              />
              <div className="absolute top-3 left-3 bg-black/60 backdrop-blur-sm text-white text-[10px] font-bold px-2 py-0.5 uppercase tracking-wider rounded">
                {item.eyebrow}
              </div>
            </div>

            {/* Info */}
            <div className="p-5 flex-1 flex flex-col justify-between">
              <div>
                <h3 className="font-heading font-bold text-lg text-yonex-dark tracking-wide uppercase leading-snug group-hover:text-yonex-blue transition-colors">
                  {item.title}
                </h3>
                <p className="mt-2 text-xs text-gray-600 line-clamp-2">
                  {item.desc}
                </p>
              </div>

              <div className="mt-5 pt-3 border-t border-gray-100 flex items-center justify-between">
                <span className="font-heading font-bold text-xs uppercase tracking-wider text-yonex-dark group-hover:text-yonex-blue transition-colors flex items-center gap-1.5">
                  {item.cta} <ArrowRight size={13} className="group-hover:translate-x-1 transition-transform" />
                </span>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}
