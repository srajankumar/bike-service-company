"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { toast } from "sonner";
import Link from "next/link";

export default function AddBikePage() {
  const [form, setForm] = useState({
    bikeMake: "",
    modelName: "",
    bikeRegistrationNumber: "",
    bikeChassisNumber: "",
    knownIssues: "",
    cost: "",
    givenDate: "",
    expectedDeliveryDate: "",
    customerName: "",
    phoneNumber: "",
    houseNo: "",
    street: "",
    landmark: "",
    city: "",
    state: "",
    pin: "",
  });
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState<{ [key: string]: string }>({});
  const router = useRouter();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: "" });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setErrors({});
    const token = localStorage.getItem("accessToken");
    const body = {
      bikeMake: form.bikeMake,
      modelName: form.modelName,
      bikeRegistrationNumber: form.bikeRegistrationNumber,
      bikeChassisNumber: form.bikeChassisNumber,
      knownIssues: form.knownIssues,
      cost: Number(form.cost),
      givenDate: form.givenDate,
      expectedDeliveryDate: form.expectedDeliveryDate,
      customer: {
        customerName: form.customerName,
        phoneNumber: form.phoneNumber,
        houseNo: form.houseNo,
        street: form.street,
        landmark: form.landmark,
        city: form.city,
        state: form.state,
        pin: form.pin,
      },
    };
    try {
      const res = await fetch("http://localhost:8080/api/bikes/save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: token ? `Bearer ${token}` : "",
        },
        body: JSON.stringify(body),
      });

      let data: { [key: string]: any; error?: string } = {};
      try {
        data = await res.json();
      } catch {
        data = { error: "Unexpected server error" };
      }

      if (res.ok) {
        setLoading(false);
        router.push("/");
      } else {
        if (res.status === 401) {
          toast.error("Expired token");
          localStorage.removeItem("accessToken");
          router.push("/login");
        } else if (data.error) {
          toast.error(data.error);
        }
        setErrors(data);
        setLoading(false);
      }
    } catch (err: any) {
      toast.error("Network error");
      setLoading(false);
    }
  };

  return (
    <div className="max-w-6xl mx-auto px-5 pb-20">
      <div className="flex justify-between py-10">
        <h1 className="text-xl font-bold underline decoration-wavy underline-offset-8 decoration-primary">Add New</h1>
        <div className="flex flex-wrap gap-3">
          <Link href={'/'}>
            <Button>Back</Button>
          </Link>
        </div>
      </div>
      <form className="grid gap-5" onSubmit={handleSubmit}>
        <div>
          <Label className="mb-2">Bike Make</Label>
          <Input name="bikeMake" value={form.bikeMake} onChange={handleChange} required />
          {errors.bikeMake && <p className="text-red-500 text-sm">{errors.bikeMake}</p>}
        </div>
        <div>
          <Label className="mb-2">Model Name</Label>
          <Input name="modelName" value={form.modelName} onChange={handleChange} required />
          {errors.modelName && <p className="text-red-500 text-sm">{errors.modelName}</p>}
        </div>
        <div>
          <Label className="mb-2">Registration Number</Label>
          <Input name="bikeRegistrationNumber" value={form.bikeRegistrationNumber} onChange={handleChange} required />
          {errors.bikeRegistrationNumber && <p className="text-red-500 text-sm">{errors.bikeRegistrationNumber}</p>}
        </div>
        <div>
          <Label className="mb-2">Chassis Number</Label>
          <Input name="bikeChassisNumber" value={form.bikeChassisNumber} onChange={handleChange} required />
          {errors.bikeChassisNumber && <p className="text-red-500 text-sm">{errors.bikeChassisNumber}</p>}
        </div>
        <div>
          <Label className="mb-2">Known Issues</Label>
          <Input name="knownIssues" value={form.knownIssues} onChange={handleChange} required />
          {errors.knownIssues && <p className="text-red-500 text-sm">{errors.knownIssues}</p>}
        </div>
        <div>
          <Label className="mb-2">Cost</Label>
          <Input name="cost" type="number" value={form.cost} onChange={handleChange} required />
          {errors.cost && <p className="text-red-500 text-sm">{errors.cost}</p>}
        </div>
        <div>
          <Label className="mb-2">Given Date</Label>
          <Input name="givenDate" type="datetime-local" value={form.givenDate} onChange={handleChange} required />
          {errors.givenDate && <p className="text-red-500 text-sm">{errors.givenDate}</p>}
        </div>
        <div>
          <Label className="mb-2">Expected Delivery Date</Label>
          <Input name="expectedDeliveryDate" type="date" value={form.expectedDeliveryDate} onChange={handleChange} required />
          {errors.expectedDeliveryDate && <p className="text-red-500 text-sm">{errors.expectedDeliveryDate}</p>}
        </div>
        <div className="font-semibold my-2 underline decoration-wavy underline-offset-4 decoration-primary">Customer Details</div>
        <div>
          <Label className="mb-2">Name</Label>
          <Input name="customerName" value={form.customerName} onChange={handleChange} required />
          {(errors.customerName || errors["customer.customerName"]) && (
            <p className="text-red-500 text-sm">
              {errors.customerName || errors["customer.customerName"]}
            </p>
          )}
        </div>
        <div>
          <Label className="mb-2">Phone Number</Label>
          <Input name="phoneNumber" type="number" value={form.phoneNumber} onChange={handleChange} required />
          {(errors.phoneNumber || errors["customer.phoneNumber"]) && (
            <p className="text-red-500 text-sm">
              {errors.phoneNumber || errors["customer.phoneNumber"]}
            </p>
          )}
        </div>
        <div>
          <Label className="mb-2">House No</Label>
          <Input name="houseNo" value={form.houseNo} onChange={handleChange} required />
          {(errors.houseNo || errors["customer.houseNo"]) && (
            <p className="text-red-500 text-sm">
              {errors.houseNo || errors["customer.houseNo"]}
            </p>
          )}
        </div>
        <div>
          <Label className="mb-2">Street</Label>
          <Input name="street" value={form.street} onChange={handleChange} required />
          {(errors.street || errors["customer.street"]) && (
            <p className="text-red-500 text-sm">
              {errors.street || errors["customer.street"]}
            </p>
          )}
        </div>
        <div>
          <Label className="mb-2">Landmark</Label>
          <Input name="landmark" value={form.landmark} onChange={handleChange} required />
          {(errors.landmark || errors["customer.landmark"]) && (
            <p className="text-red-500 text-sm">
              {errors.landmark || errors["customer.landmark"]}
            </p>
          )}
        </div>
        <div>
          <Label className="mb-2">City</Label>
          <Input name="city" value={form.city} onChange={handleChange} required />
          {(errors.city || errors["customer.city"]) && (
            <p className="text-red-500 text-sm">
              {errors.city || errors["customer.city"]}
            </p>
          )}
        </div>
        <div>
          <Label className="mb-2">State</Label>
          <Input name="state" value={form.state} onChange={handleChange} required />
          {(errors.state || errors["customer.state"]) && (
            <p className="text-red-500 text-sm">
              {errors.state || errors["customer.state"]}
            </p>
          )}
        </div>
        <div>
          <Label className="mb-2">Pin</Label>
          <Input name="pin" type="number" value={form.pin} onChange={handleChange} required />
          {(errors.pin || errors["customer.pin"]) && (
            <p className="text-red-500 text-sm">
              {errors.pin || errors["customer.pin"]}
            </p>
          )}
        </div>
        <Button type="submit" disabled={loading}>
          Add {loading && (<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path fill="currentColor" d="M12 2A10 10 0 1 0 22 12A10 10 0 0 0 12 2Zm0 18a8 8 0 1 1 8-8A8 8 0 0 1 12 20Z" opacity="0.5" /><path fill="currentColor" d="M20 12h2A10 10 0 0 0 12 2V4A8 8 0 0 1 20 12Z"><animateTransform attributeName="transform" dur="1s" from="0 12 12" repeatCount="indefinite" to="360 12 12" type="rotate" /></path></svg>)}
        </Button>
      </form>
    </div>
  );
}