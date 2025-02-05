#include <thread>
#include <iostream>
#include <exception>

int main(void) {
    std::thread exceptional_task([](void) -> void {
        std::cout << "Thread B started!\n";
        throw std::runtime_error("Thread B aborted!\n");
    });

    std::cout << "Thread A started!\n";

    for (int i = 0; i < 50; i++) {
        std::cout << "Thread A doing boring work [-_-]\n";
    }

    std::cout << "Thread A continues\n";

    exceptional_task.join();

    std::cout << "Thread A finished!\n";
}
