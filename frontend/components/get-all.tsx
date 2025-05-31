"use client";

import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
} from "@/components/ui/dialog";
import { Skeleton } from "@/components/ui/skeleton"
import { ScrollArea } from "@/components/ui/scroll-area"
import { toast } from "sonner";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import {
  Table,
  TableHeader,
  TableBody,
  TableRow,
  TableHead,
  TableCell,
} from "@/components/ui/table";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

type Customer = {
  customerId: number;
  customerName: string;
  phoneNumber: string;
  houseNo: string;
  street: string;
  landmark: string;
  city: string;
  state: string;
  pin: string;
};

type Bike = {
  bikeId: number;
  bikeMake: string;
  modelName: string;
  bikeRegistrationNumber: string;
  bikeChassisNumber: string;
  knownIssues: string;
  cost: number;
  givenDate: string;
  expectedDeliveryDate: string;
  createdDateAndTime: string;
  updatedDateAndTime: string;
  customer: Customer;
};

const PAGE_SIZE = 6;

export default function GetAll() {
  const [selectedBike, setSelectedBike] = useState<Bike | null>(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState<number | null>(null);
  const [bikes, setBikes] = useState<Bike[]>([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [search, setSearch] = useState("");
  const [filtered, setFiltered] = useState<Bike[]>([]);

  const router = useRouter();

  const handleDelete = async (bikeId: number) => {
    const token = localStorage.getItem("accessToken");
    try {
      const res = await fetch(`http://localhost:8080/api/bikes/${bikeId}`, {
        method: "DELETE",
        headers: {
          Authorization: token ? `Bearer ${token}` : "",
        },
      });
      if (res.status === 401) {
        toast.error("Expired token");
        localStorage.removeItem("accessToken");
        router.push("/login");
        return;
      }
      if (res.ok) {
        setBikes((prev) => prev.filter((b) => b.bikeId !== bikeId));
        toast.success("Bike deleted successfully");
      } else {
        toast.error("Failed to delete bike.");
      }
    } catch {
      toast.error("Network error");
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    fetch("http://localhost:8080/api/bikes", {
      headers: {
        Authorization: token ? `Bearer ${token}` : "",
      },
    })
      .then(async (res) => {
        if (res.status === 401) {
          localStorage.removeItem("accessToken");
          setLoading(false);
          return [];
        }
        return res.json();
      })
      .then((data) => {
        setBikes(data);
        setLoading(false);
      });
  }, []);

  useEffect(() => {
    const s = search.trim().toLowerCase();
    if (!s) {
      setFiltered(bikes);
    } else {
      setFiltered(
        bikes.filter(
          (b) =>
            b.bikeMake.toLowerCase().includes(s) ||
            b.modelName.toLowerCase().includes(s) ||
            b.bikeRegistrationNumber.toLowerCase().includes(s) ||
            b.customer.customerName.toLowerCase().includes(s)
        )
      );
    }
    setPage(1);
  }, [search, bikes]);

  const totalPages = Math.ceil(filtered.length / PAGE_SIZE);
  const paginated = filtered.slice((page - 1) * PAGE_SIZE, page * PAGE_SIZE);

  if (loading) return
  <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-5 mb-5">
      <Input
        placeholder="Search by make, model, reg. no, or customer"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />
    </div>
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>ID</TableHead>
          <TableHead>Make</TableHead>
          <TableHead>Model</TableHead>
          <TableHead>Reg. No.</TableHead>
          <TableHead>Customer</TableHead>
          <TableHead>Known Issues</TableHead>
          <TableHead>Cost</TableHead>
          <TableHead>Given Date</TableHead>
          <TableHead>Expected Delivery</TableHead>
          <TableHead className="text-center">Action</TableHead>
        </TableRow>
      </TableHeader>
    </Table>
    <Skeleton className="h-10 w-full my-2" />
    <Skeleton className="h-10 w-full my-2" />
    <Skeleton className="h-10 w-full my-2" />
    <Skeleton className="h-10 w-full my-2" />
    <Skeleton className="h-10 w-full my-2" />
    <div className="flex items-center justify-between mt-5">
      <Button
        disabled
        variant="outline"
      >
        Previous
      </Button>
      <span className="text-sm text-muted-foreground">
        Page 1 of 1
      </span>
      <Button
        disabled
        variant="outline"
      >
        Next
      </Button>
    </div>
  </Dialog>;

  return (
    <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
      <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-5 mb-5">
        <Input
          placeholder="Search by make, model, reg. no, or customer"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>ID</TableHead>
            <TableHead>Make</TableHead>
            <TableHead>Model</TableHead>
            <TableHead>Reg. No.</TableHead>
            <TableHead>Customer</TableHead>
            <TableHead>Known Issues</TableHead>
            <TableHead>Cost</TableHead>
            <TableHead>Given Date</TableHead>
            <TableHead>Expected Delivery</TableHead>
            <TableHead className="text-center">Action</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {paginated.map((bike) => (
            <TableRow
              key={bike.bikeId}
              className="cursor-pointer"
              onClick={() => {
                if (deleteDialogOpen) return; // Prevent opening details if delete dialog is open
                setSelectedBike(bike);
                setDialogOpen(true);
              }}
            >
              <TableCell>{bike.bikeId}</TableCell>
              <TableCell>{bike.bikeMake}</TableCell>
              <TableCell>{bike.modelName}</TableCell>
              <TableCell>{bike.bikeRegistrationNumber}</TableCell>
              <TableCell>{bike.customer.customerName}</TableCell>
              <TableCell>{bike.knownIssues}</TableCell>
              <TableCell>{bike.cost}</TableCell>
              <TableCell>
                {new Date(bike.givenDate).toLocaleString()}
              </TableCell>
              <TableCell>{bike.expectedDeliveryDate}</TableCell>
              <TableCell className="flex gap-2">
                <Button
                  variant="outline"
                  onClick={e => {
                    e.stopPropagation();
                    router.push(`/update?id=${bike.bikeId}`);
                  }}
                  size="sm"
                >
                  Update
                </Button>
                <AlertDialog
                  open={deleteDialogOpen === bike.bikeId}
                  onOpenChange={open => setDeleteDialogOpen(open ? bike.bikeId : null)}
                >
                  <AlertDialogTrigger asChild>
                    <Button
                      variant="destructive"
                      size="sm"
                      onClick={e => {
                        e.stopPropagation();
                        setDeleteDialogOpen(bike.bikeId);
                      }}
                    >
                      Delete
                    </Button>
                  </AlertDialogTrigger>
                  <AlertDialogContent>
                    <AlertDialogHeader>
                      <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>
                      <AlertDialogDescription>
                        This action cannot be undone. This will permanently delete this bike and remove its data from our servers.
                      </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                      <AlertDialogAction
                        onClick={() => {
                          handleDelete(bike.bikeId);
                          setDeleteDialogOpen(null);
                        }}
                      >
                        Delete
                      </AlertDialogAction>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialog>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Bike Details</DialogTitle>
            <DialogDescription>
              {selectedBike && (
                <ScrollArea className="h-[400px]">
                  <div className="space-y-2 text-left py-3">
                    <div><b>ID:</b> {selectedBike.bikeId}</div>
                    <div><b>Make:</b> {selectedBike.bikeMake}</div>
                    <div><b>Model:</b> {selectedBike.modelName}</div>
                    <div><b>Registration No:</b> {selectedBike.bikeRegistrationNumber}</div>
                    <div><b>Chassis No:</b> {selectedBike.bikeChassisNumber}</div>
                    <div><b>Known Issues:</b> {selectedBike.knownIssues}</div>
                    <div><b>Cost:</b> {selectedBike.cost}</div>
                    <div><b>Given Date:</b> {new Date(selectedBike.givenDate).toLocaleString()}</div>
                    <div><b>Expected Delivery:</b> {selectedBike.expectedDeliveryDate}</div>
                    <div><b>Created:</b> {selectedBike.createdDateAndTime}</div>
                    <div><b>Updated:</b> {selectedBike.updatedDateAndTime}</div>
                    <div className="mt-2 font-semibold">Customer Details</div>
                    <div><b>Name:</b> {selectedBike.customer.customerName}</div>
                    <div><b>Phone:</b> {selectedBike.customer.phoneNumber}</div>
                    <div><b>House No:</b> {selectedBike.customer.houseNo}</div>
                    <div><b>Street:</b> {selectedBike.customer.street}</div>
                    <div><b>Landmark:</b> {selectedBike.customer.landmark}</div>
                    <div><b>City:</b> {selectedBike.customer.city}</div>
                    <div><b>State:</b> {selectedBike.customer.state}</div>
                    <div><b>Pin:</b> {selectedBike.customer.pin}</div>
                  </div>
                </ScrollArea>
              )}
            </DialogDescription>
          </DialogHeader>
        </DialogContent>
      </Dialog>
      <div className="flex items-center justify-between mt-5">
        <Button
          onClick={() => setPage((p) => Math.max(1, p - 1))}
          disabled={page === 1}
          variant="outline"
        >
          Previous
        </Button>
        <span className="text-sm text-muted-foreground">
          Page {page} of {totalPages || 1}
        </span>
        <Button
          onClick={() => setPage((p) => Math.min(totalPages, p + 1))}
          disabled={page === totalPages || totalPages === 0}
          variant="outline"
        >
          Next
        </Button>
      </div>
    </Dialog>
  );
}