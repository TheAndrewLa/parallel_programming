#include <thread>
#include <iostream>
#include <exception>

int main(void) {
    std::cout << "Thread A started!\n";

    std::thread exceptional_task([](void) -> void {
        std::this_thread::sleep_for(std::chrono::milliseconds(100));
        std::cout << "Thread B started!\n";
        throw std::runtime_error("Thread B aborted!\n");
    });

    for (int i = 0; i < 1000; i++) {
        std::cout << "Thread A doing boring work [-_-]\n";
    }

    std::cout << "Thread A continues\n";

    exceptional_task.join();

    std::cout << "Thread A finished!\n";
}
