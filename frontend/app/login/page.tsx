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
  const [loading, setLoading] = useState<boolean>(false);
  const router = useRouter();

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ usernameOrEmail, password }),
      });
      if (!res.ok) {
        setLoading(false);
        throw new Error("Invalid credentials");
      }
      const data = await res.json();
      localStorage.setItem("accessToken", data.jwtTokens);
      router.push("/");
    } catch (err: any) {
      setLoading(false);
      setError(err.message);
      toast.error(err.message);
    }
  };

  return (
    <div className="flex flex-col gap-5 min-h-dvh items-center justify-center">
      <h2 className="text-2xl font-bold">Login</h2>
      <form className="grid gap-5 md:max-w-sm px-5 w-full" onSubmit={handleSubmit}>
        <div>
          <Label className="mb-2">Username or Email</Label>
          <Input
            type="text"
            value={usernameOrEmail}
            onChange={(e: ChangeEvent<HTMLInputElement>) => setUsernameOrEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <Label className="mb-2">Password</Label>
          <Input
            type="password"
            value={password}
            onChange={(e: ChangeEvent<HTMLInputElement>) => setPassword(e.target.value)}
            required
          />
        </div>
        <Button type="submit" className="w-full" disabled={loading}>
          Login {loading && (<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path fill="currentColor" d="M12 2A10 10 0 1 0 22 12A10 10 0 0 0 12 2Zm0 18a8 8 0 1 1 8-8A8 8 0 0 1 12 20Z" opacity="0.5" /><path fill="currentColor" d="M20 12h2A10 10 0 0 0 12 2V4A8 8 0 0 1 20 12Z"><animateTransform attributeName="transform" dur="1s" from="0 12 12" repeatCount="indefinite" to="360 12 12" type="rotate" /></path></svg>)}
        </Button>
      </form>
    </div>
  );
}