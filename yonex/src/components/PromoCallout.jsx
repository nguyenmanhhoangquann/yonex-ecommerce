import React from 'react';
import { ArrowRight, Gift } from 'lucide-react';

export default function PromoCallout() {
  return (
    <section className="bg-gray-50 border-y border-gray-200 py-10 lg:py-14">
      <div className="max-w-4xl mx-auto px-6 text-center">
        <div className="inline-flex items-center gap-2 text-yonex-blue font-heading font-bold text-xs uppercase tracking-widest mb-2">
          <Gift size={16} />
          <span>Limited Time Exclusive Offer</span>
        </div>

        <h2 className="font-heading font-black text-2xl sm:text-3xl lg:text-4xl text-yonex-dark tracking-wide uppercase">
          GET TOUR PLATINUM ON US
        </h2>

        <p className="mt-3 text-sm sm:text-base text-gray-600 font-normal leading-relaxed max-w-2xl mx-auto">
          Receive a complimentary can of TOUR PLATINUM balls with any tennis racquet purchase, while supplies last.* The official ball of the ATP 2026 Asia Swing and Davis Cup Final 8.
        </p>

        <div className="mt-6">
          <a
            href="#products"
            className="inline-flex items-center gap-2 border-2 border-yonex-dark text-yonex-dark font-heading font-bold text-xs sm:text-sm tracking-widest uppercase px-8 py-3.5 rounded hover:bg-yonex-dark hover:text-white transition-all transform hover:-translate-y-0.5"
          >
            <span>SHOP TENNIS RACQUETS</span>
            <ArrowRight size={14} />
          </a>
        </div>
      </div>
    </section>
  );
}
