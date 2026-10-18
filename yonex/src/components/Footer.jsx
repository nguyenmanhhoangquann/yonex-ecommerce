import React from 'react';
import YonexLogo from './YonexLogo';
import { Globe } from 'lucide-react';

export default function Footer() {
  return (
    <footer className="bg-[#191A1C] text-white pt-16 pb-12 border-t border-gray-800">
      <div className="max-w-[1920px] mx-auto px-4 sm:px-8">
        {/* Centered Brand Logo */}
        <div className="flex justify-center mb-12 pb-8 border-b border-gray-800">
          <YonexLogo className="h-9 sm:h-11" light={true} />
        </div>

        {/* 4 Navigation Columns */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-10 mb-12">
          {/* Column 1: Get Help */}
          <div>
            <h4 className="font-heading font-bold text-lg text-white uppercase tracking-wider mb-4 pb-1 border-b border-gray-800">
              GET HELP
            </h4>
            <ul className="space-y-2.5 text-xs sm:text-sm text-gray-400 font-medium">
              <li><a href="#account" className="hover:text-white transition-colors">My Account</a></li>
              <li><a href="#track-order" className="hover:text-white transition-colors">Track Orders</a></li>
              <li><a href="#returns" className="hover:text-white transition-colors">Returns & Exchanges</a></li>
              <li><a href="#warranty" className="hover:text-white transition-colors">Warranty Policy</a></li>
              <li><a href="#contact" className="hover:text-white transition-colors">Contact Us</a></li>
              <li><a href="#faq" className="hover:text-white transition-colors">Frequently Asked Questions</a></li>
              <li><a href="#stringing" className="hover:text-white transition-colors">Stringing Instructions & Patterns</a></li>
            </ul>
          </div>

          {/* Column 2: Company */}
          <div>
            <h4 className="font-heading font-bold text-lg text-white uppercase tracking-wider mb-4 pb-1 border-b border-gray-800">
              COMPANY
            </h4>
            <ul className="space-y-2.5 text-xs sm:text-sm text-gray-400 font-medium">
              <li><a href="#about" className="hover:text-white transition-colors">About Yonex</a></li>
              <li><a href="#made-by-yonex" className="hover:text-white transition-colors">Made by Yonex (Niigata, Japan)</a></li>
              <li><a href="#news" className="hover:text-white transition-colors">Global News & Press</a></li>
              <li><a href="#careers" className="hover:text-white transition-colors">Careers</a></li>
              <li><a href="#group" className="hover:text-white transition-colors">Yonex Group</a></li>
              <li><a href="#distributors" className="hover:text-white transition-colors">Authorized Distributors</a></li>
              <li><a href="#investors" className="hover:text-white transition-colors">Investor Relations</a></li>
            </ul>
          </div>

          {/* Column 3: Legal */}
          <div>
            <h4 className="font-heading font-bold text-lg text-white uppercase tracking-wider mb-4 pb-1 border-b border-gray-800">
              LEGAL & PRIVACY
            </h4>
            <ul className="space-y-2.5 text-xs sm:text-sm text-gray-400 font-medium">
              <li><a href="#privacy" className="hover:text-white transition-colors">Privacy Policy</a></li>
              <li><a href="#terms" className="hover:text-white transition-colors">Terms & Conditions</a></li>
              <li><a href="#counterfeits" className="hover:text-white transition-colors">Spotting Counterfeits</a></li>
              <li><a href="#accessibility" className="hover:text-white transition-colors">Accessibility Statement</a></li>
              <li>
                <button className="flex items-center gap-2 hover:text-white transition-colors text-left">
                  <span>Your Privacy Choices</span>
                  <span className="w-6 h-3 bg-blue-600 rounded-full inline-block"></span>
                </button>
              </li>
            </ul>
          </div>

          {/* Column 4: Social & Follow */}
          <div>
            <h4 className="font-heading font-bold text-lg text-white uppercase tracking-wider mb-4 pb-1 border-b border-gray-800">
              CONNECT WITH US
            </h4>
            <p className="text-xs text-gray-400 mb-4 font-normal">
              Follow @YonexUSA for tournament highlights, gear unboxings, and player stories.
            </p>

            {/* Social Icons */}
            <div className="flex items-center gap-3">
              {[
                { name: "YouTube", href: "https://youtube.com/@YonexUSAOfficial", icon: "YT" },
                { name: "Instagram", href: "https://instagram.com/yonex_usa", icon: "IG" },
                { name: "Facebook", href: "https://facebook.com/YonexUSA", icon: "FB" },
                { name: "LinkedIn", href: "https://linkedin.com/company/yonexusa", icon: "IN" },
                { name: "TikTok", href: "https://tiktok.com/@yonex_usa", icon: "TT" }
              ].map((soc, i) => (
                <a
                  key={i}
                  href={soc.href}
                  target="_blank"
                  rel="noreferrer"
                  className="w-9 h-9 rounded bg-gray-800 text-gray-300 hover:bg-yonex-blue hover:text-white transition-colors flex items-center justify-center font-bold text-xs"
                  title={soc.name}
                >
                  {soc.icon}
                </a>
              ))}
            </div>

            <div className="mt-6 pt-4 border-t border-gray-800">
              <div className="flex items-center gap-2 text-xs text-gray-400">
                <Globe size={14} className="text-yonex-blue" />
                <span className="font-semibold text-gray-300">United States / English ($ USD)</span>
              </div>
            </div>
          </div>
        </div>

        {/* Bottom bar */}
        <div className="pt-8 border-t border-gray-800 flex flex-col md:flex-row items-center justify-between gap-4 text-xs text-gray-500 font-medium">
          <div>
            <p>© {new Date().getFullYear()} Yonex USA Inc. All rights reserved. Technology Driven Excellence.</p>
          </div>

          {/* Payment Badges */}
          <div className="flex items-center gap-2 text-gray-400">
            <span className="px-2 py-1 bg-gray-800 rounded font-bold text-[10px] text-gray-300 tracking-wider">VISA</span>
            <span className="px-2 py-1 bg-gray-800 rounded font-bold text-[10px] text-gray-300 tracking-wider">MASTERCARD</span>
            <span className="px-2 py-1 bg-gray-800 rounded font-bold text-[10px] text-gray-300 tracking-wider">AMEX</span>
            <span className="px-2 py-1 bg-gray-800 rounded font-bold text-[10px] text-gray-300 tracking-wider">APPLE PAY</span>
            <span className="px-2 py-1 bg-gray-800 rounded font-bold text-[10px] text-gray-300 tracking-wider">PAYPAL</span>
          </div>
        </div>
      </div>
    </footer>
  );
}
