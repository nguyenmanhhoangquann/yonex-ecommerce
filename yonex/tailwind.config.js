/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        yonex: {
          blue: "#006CB7",
          darkBlue: "#005088",
          green: "#008840",
          accentGreen: "#00C853",
          dark: "#191A1C",
          charcoal: "#121212",
          gray: "#F5F5F5",
          border: "#E5E7EB",
          muted: "#6B7280",
          red: "#B7002A"
        }
      },
      fontFamily: {
        heading: ["Oswald", "sans-serif"],
        body: ["Barlow", "sans-serif"],
      },
      letterSpacing: {
        tighter: "-0.04em",
        tight: "-0.02em",
        wide: "0.05em",
        wider: "0.1em",
        widest: "0.2em",
      }
    },
  },
  plugins: [],
}
