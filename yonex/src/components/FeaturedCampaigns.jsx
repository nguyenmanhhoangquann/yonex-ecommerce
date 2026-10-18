import React from 'react';
import { ArrowRight } from 'lucide-react';
import { FEATURED_CAMPAIGNS } from '../data/yonexData';

export default function FeaturedCampaigns() {
  return (
    <section className="py-6 max-w-[1920px] mx-auto px-4 sm:px-8">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 lg:gap-8">
        {FEATURED_CAMPAIGNS.map((camp) => (
          <div 
            key={camp.id}
            className="group relative overflow-hidden bg-black rounded-sm aspect-[16/14] sm:aspect-[16/11] flex flex-col justify-end shadow-md hover:shadow-2xl transition-all duration-500"
          >
            {/* Image */}
            <img 
              src={camp.image} 
              alt={camp.title}
              className="absolute inset-0 w-full h-full object-cover object-center group-hover:scale-105 transition-transform duration-700 ease-out"
              loading="lazy"
            />

            {/* Gradient */}
            <div className="absolute inset-0 bg-gradient-to-t from-black/90 via-black/40 to-transparent" />

            {/* Text details */}
            <div className="relative z-10 p-6 sm:p-10 text-white">
              <p className="text-xs sm:text-sm font-semibold tracking-widest text-yonex-green uppercase mb-2">
                {camp.eyebrow}
              </p>

              <h3 className="font-heading font-black text-2xl sm:text-3xl lg:text-4xl text-white tracking-tight uppercase leading-tight">
                {camp.title}
              </h3>

              <p className="mt-2 text-xs sm:text-sm text-gray-300 font-normal max-w-md line-clamp-2">
                {camp.description}
              </p>

              <div className="mt-5">
                <a
                  href="#collection"
                  className="inline-flex items-center gap-2 bg-white text-yonex-dark font-heading font-bold text-xs sm:text-sm tracking-widest uppercase px-6 py-3 rounded group-hover:bg-yonex-blue group-hover:text-white transition-all transform group-hover:translate-x-1"
                >
                  <span>{camp.cta}</span>
                  <ArrowRight size={14} />
                </a>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}
