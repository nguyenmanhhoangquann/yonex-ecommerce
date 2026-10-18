import React, { useState } from 'react';
import { ArrowRight, CheckCircle2, Mail } from 'lucide-react';

export default function Newsletter() {
  const [email, setEmail] = useState('');
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (email && email.includes('@')) {
      setSubmitted(true);
      setTimeout(() => {
        setEmail('');
      }, 4000);
    }
  };

  return (
    <section className="bg-white border-t border-gray-200 py-12 lg:py-16">
      <div className="max-w-4xl mx-auto px-6 text-center">
        <div className="w-12 h-12 bg-blue-50 text-yonex-blue rounded-full flex items-center justify-center mx-auto mb-4">
          <Mail size={22} />
        </div>

        <h2 className="font-heading font-black text-2xl sm:text-3xl lg:text-4xl text-yonex-dark tracking-wide uppercase">
          JOIN YONEX INSIDER
        </h2>

        <p className="mt-2 text-sm sm:text-base text-gray-600 max-w-md mx-auto">
          Be the first to know about new product drops, exclusive athlete interviews, and special tournament promotions.
        </p>

        {submitted ? (
          <div className="mt-6 p-4 bg-green-50 border border-green-200 text-green-800 rounded flex items-center justify-center gap-2 max-w-md mx-auto animate-fade-in text-sm font-semibold">
            <CheckCircle2 size={18} className="text-green-600 shrink-0" />
            <span>Thank you for joining Yonex Insider! Check your inbox soon.</span>
          </div>
        ) : (
          <form onSubmit={handleSubmit} className="mt-6 max-w-md mx-auto flex gap-2">
            <input 
              type="email" 
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email address"
              className="flex-1 px-4 py-3 border border-gray-300 rounded text-sm text-yonex-dark focus:outline-none focus:border-yonex-blue focus:ring-1 focus:ring-yonex-blue"
            />
            <button
              type="submit"
              className="bg-yonex-blue text-white px-6 py-3 font-heading font-bold text-xs uppercase tracking-wider rounded hover:bg-yonex-darkBlue transition-colors flex items-center gap-1 shrink-0"
              aria-label="Subscribe"
            >
              <span>SUBSCRIBE</span>
              <ArrowRight size={14} />
            </button>
          </form>
        )}

        <p className="text-[11px] text-gray-400 mt-3">
          By signing up, you agree to receive marketing communications and accept our Privacy Policy.
        </p>
      </div>
    </section>
  );
}
