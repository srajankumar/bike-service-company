"use client";

import GetAll from "@/components/get-all";
import { Button } from "@/components/ui/button";
import Link from "next/link";
import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { Plus } from "lucide-react";

export default function Home() {
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (!token) {
      router.push("/login");
    }
  }, [router]);

  return (
    <div className="max-w-6xl mx-auto px-5 pb-20">
      <div className="flex gap-5 justify-between py-10">
        <h1 className="text-xl font-bold underline decoration-wavy underline-offset-8 decoration-primary">Bike Service Company</h1>
        <div className="flex flex-wrap gap-3">
          <Link href={'/add'}>
            <Button><Plus /></Button>
          </Link>
        </div>
      </div>
      <GetAll />
    </div>
  );
}