"use client";

import { useState, FormEvent, ChangeEvent } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { toast } from "sonner";

export default function LoginPage() {
  const [usernameOrEmail, setUsernameOrEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState<string>("");
  const router = useRouter();

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError("");
    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ usernameOrEmail, password }),
      });
      if (!res.ok) {
        throw new Error("Invalid credentials");
      }
      const data = await res.json();
      // Assuming the backend returns { jwtTokens: "..." }
      localStorage.setItem("accessToken", data.jwtTokens);
      router.push("/"); // Redirect to home or dashboard
    } catch (err: any) {
      setError(err.message);
      toast.error(err.message);
    }
  };

  return (
    <div className="flex flex-col gap-3 min-h-dvh items-center justify-center">
      <h2 className="text-3xl font-bold">Login</h2>
      <form className="grid gap-5 md:max-w-sm px-5 w-full" onSubmit={handleSubmit}>
        <div>
          <Label className="mb-2">Username:</Label>
          <Input
            type="text"
            value={usernameOrEmail}
            onChange={(e: ChangeEvent<HTMLInputElement>) => setUsernameOrEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <Label className="mb-2">Password:</Label>
          <Input
            type="password"
            value={password}
            onChange={(e: ChangeEvent<HTMLInputElement>) => setPassword(e.target.value)}
            required
          />
        </div>
        <Button type="submit">
          Login
        </Button>
      </form>
    </div>
  );
}