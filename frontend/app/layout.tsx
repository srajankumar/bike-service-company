import type { Metadata } from "next";
import "./globals.css";

import { Toaster } from "@/components/ui/sonner"

export const metadata: Metadata = {
  title: "Bike Service Company",
  description: "Spring Boot CRUD Application",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body
        className={`antialiased`}
      >
        {children}
        <Toaster richColors />
      </body>
    </html>
  );
}
